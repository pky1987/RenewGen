package com.example.re

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.math.min

data class RenewableEnergyTopic(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("details") val details: List<String>,
    @SerializedName("keywords") val keywords: List<String>,
    @SerializedName("common_misspellings") val commonMisspellings: Map<String, Any>,
    @SerializedName("related_topics") val relatedTopics: List<String>? = null,
    @SerializedName("follow_up_questions") val followUpQuestions: List<String>? = null
)

class RenewableEnergyKnowledge(private val context: Context) {
    private var topics: List<RenewableEnergyTopic> = emptyList()
    private var responseTemplates: Map<String, List<String>> = emptyMap()
    private var queryHandlers: Map<String, Map<String, Map<String, Any>>> = emptyMap()
    private var conversationContext: ConversationContext = ConversationContext()
    private var lastSpellingCorrection: SpellingCorrection? = null
    
    companion object {
        private const val LEVENSHTEIN_THRESHOLD = 0.8 // Similarity threshold for fuzzy matching
        private const val MAX_SUGGESTIONS = 3 // Maximum number of related suggestions
    }
    
    init {
        loadKnowledgeBase()
    }

    private data class ConversationContext(
        var lastTopic: String = "",
        var questionCount: Int = 0,
        var topicHistory: MutableList<String> = mutableListOf(),
        var unansweredQueries: MutableList<String> = mutableListOf()
    )

    private fun loadKnowledgeBase() {
        try {
            val jsonString = context.assets.open("knowledge/renewable_energy.json")
                .bufferedReader()
                .use { it.readText() }
            
            val gson = Gson()
            val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
            
            topics = gson.fromJson(
                jsonObject.getAsJsonArray("topics"),
                object : TypeToken<List<RenewableEnergyTopic>>() {}.type
            )
            
            responseTemplates = gson.fromJson(
                jsonObject.getAsJsonObject("response_templates"),
                object : TypeToken<Map<String, List<String>>>() {}.type
            )
            
            queryHandlers = gson.fromJson(
                jsonObject.getAsJsonObject("query_handlers"),
                object : TypeToken<Map<String, Map<String, Map<String, Any>>>>() {}.type
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun findResponse(query: String): String {
        val normalizedQuery = query.lowercase().trim()
        conversationContext.questionCount++

        // Check for meta-questions about the conversation
        when {
            normalizedQuery.contains("previous topic") || normalizedQuery.contains("last topic") -> {
                return handlePreviousTopicQuery()
            }
            normalizedQuery.contains("related to") -> {
                return handleRelatedTopicsQuery(normalizedQuery)
            }
        }

        // Check general inquiries first
        queryHandlers["general_inquiries"]?.forEach { (key, value) ->
            if (normalizedQuery.contains(key)) {
                val response = value["response"] as? String
                if (response != null) {
                    updateContext(key)
                    return formatResponseWithFollowUp(response, value["follow_up_suggestions"] as? List<String>)
                }
            }
        }

        // Try exact matches first
        var bestMatch = findExactMatch(normalizedQuery)
        if (bestMatch != null) {
            updateContext(bestMatch.title)
            return formatResponse(bestMatch, null)
        }

        // Try spelling corrections
        val spellingMatch = findSpellingMatch(normalizedQuery)
        if (spellingMatch != null) {
            updateContext(spellingMatch.title)
            return formatResponse(spellingMatch, lastSpellingCorrection)
        }

        // Try fuzzy matching
        bestMatch = findFuzzyMatch(normalizedQuery)
        if (bestMatch != null) {
            updateContext(bestMatch.title)
            return formatResponse(bestMatch, null)
        }

        // If still no match, store the query and return smart suggestions
        conversationContext.unansweredQueries.add(normalizedQuery)
        return generateSmartFallbackResponse(normalizedQuery)
    }

    private fun findExactMatch(query: String): RenewableEnergyTopic? {
        return topics.find { topic ->
            topic.keywords.any { keyword ->
                query.contains(keyword.lowercase())
            }
        }
    }

    private fun findSpellingMatch(query: String): RenewableEnergyTopic? {
        topics.forEach { topic ->
            topic.commonMisspellings.forEach { (misspelled, correct) ->
                if (query.contains(misspelled.lowercase())) {
                    val correctTerm = when (correct) {
                        is String -> correct
                        is List<*> -> (correct as List<String>).first()
                        else -> topic.title
                    }
                    lastSpellingCorrection = SpellingCorrection(misspelled, correctTerm)
                    return topic
                }
            }
        }
        return null
    }

    private fun findFuzzyMatch(query: String): RenewableEnergyTopic? {
        var bestSimilarity = 0.0
        var bestMatch: RenewableEnergyTopic? = null

        topics.forEach { topic ->
            val similarity = calculateSimilarity(query, topic.title.lowercase())
            if (similarity > bestSimilarity && similarity > LEVENSHTEIN_THRESHOLD) {
                bestSimilarity = similarity
                bestMatch = topic
            }
        }
        return bestMatch
    }

    private fun calculateSimilarity(s1: String, s2: String): Double {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j
        
        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                dp[i][j] = min(
                    min(dp[i-1][j] + 1, dp[i][j-1] + 1),
                    dp[i-1][j-1] + if (s1[i-1] == s2[j-1]) 0 else 1
                )
            }
        }
        
        val maxLength = maxOf(s1.length, s2.length)
        return 1 - (dp[s1.length][s2.length].toDouble() / maxLength)
    }

    private fun handlePreviousTopicQuery(): String {
        return if (conversationContext.topicHistory.isNotEmpty()) {
            val lastTopic = conversationContext.topicHistory.last()
            "We were discussing $lastTopic. Would you like to know more about it?"
        } else {
            "We haven't discussed any specific topic yet. What would you like to learn about?"
        }
    }

    private fun handleRelatedTopicsQuery(query: String): String {
        val topicName = query.substringAfter("related to").trim()
        val topic = topics.find { it.title.lowercase().contains(topicName) }
        
        return if (topic != null && !topic.relatedTopics.isNullOrEmpty()) {
            "Topics related to ${topic.title}:\n" + 
            topic.relatedTopics.joinToString("\n") { "• $it" }
        } else {
            "I couldn't find related topics for '$topicName'. Would you like to know about other renewable energy sources?"
        }
    }

    private fun updateContext(topic: String) {
        conversationContext.lastTopic = topic
        conversationContext.topicHistory.add(topic)
        if (conversationContext.topicHistory.size > 5) {
            conversationContext.topicHistory.removeAt(0)
        }
    }

    private fun generateSmartFallbackResponse(query: String): String {
        val suggestions = topics
            .sortedByDescending { calculateSimilarity(query, it.title.lowercase()) }
            .take(MAX_SUGGESTIONS)
            .map { it.title }

        return buildString {
            appendLine("I'm not quite sure about that specific query, but here are some relevant topics you might be interested in:")
            appendLine()
            suggestions.forEach { suggestion ->
                appendLine("• $suggestion")
            }
            appendLine()
            appendLine("Would you like to learn more about any of these topics?")
        }
    }

    private data class SpellingCorrection(
        val misspelled: String,
        val correct: String
    )

    private fun formatResponse(
        topic: RenewableEnergyTopic,
        spellingCorrection: SpellingCorrection?
    ): String {
        val response = buildString {
            appendLine(topic.description)
            appendLine()
            topic.details.forEach { detail ->
                appendLine("• $detail")
            }
            
            // Add follow-up questions if available
            if (!topic.followUpQuestions.isNullOrEmpty()) {
                appendLine()
                appendLine("You might also want to know:")
                topic.followUpQuestions.take(2).forEach { question ->
                    appendLine("• $question")
                }
            }
            
            // Add related topics if available
            if (!topic.relatedTopics.isNullOrEmpty()) {
                appendLine()
                appendLine("Related topics:")
                topic.relatedTopics.take(2).forEach { relatedTopic ->
                    appendLine("• $relatedTopic")
                }
            }
        }

        return if (spellingCorrection != null) {
            responseTemplates["spelling_correction"]?.random()
                ?.replace("{correct_term}", spellingCorrection.correct)
                ?.replace("{misspelled_term}", spellingCorrection.misspelled)
                ?.replace("{response}", response)
                ?: response
        } else {
            response
        }
    }

    private fun formatResponseWithFollowUp(response: String, followUps: List<String>?): String {
        return buildString {
            appendLine(response)
            if (!followUps.isNullOrEmpty()) {
                appendLine()
                appendLine("Would you like to:")
                followUps.forEach { suggestion ->
                    appendLine("• $suggestion")
                }
            }
        }
    }
} 