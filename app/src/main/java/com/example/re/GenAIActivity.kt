package com.example.re

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenAIActivity : AppCompatActivity() {
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var knowledgeBase: RenewableEnergyKnowledge
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: TextInputEditText
    private lateinit var sendButton: MaterialButton
    private lateinit var suggestionChipGroup: ChipGroup
    private lateinit var statusChip: Chip
    private lateinit var progressIndicator: LinearProgressIndicator
    private val messages = mutableListOf<ChatMessage>()

    companion object {
        // Static knowledge base for sharing between activities
        private var sharedKnowledgeBase: Map<String, List<String>> = mapOf(
            "solar" to listOf(
                "Solar energy is captured through photovoltaic cells in solar panels",
                "Solar power can be used for electricity generation and water heating",
                "Modern solar panels can achieve efficiency rates of 15-20%",
                "Solar energy storage systems allow for 24/7 power availability"
            ),
            "wind" to listOf(
                "Wind turbines convert kinetic energy of wind into electrical energy",
                "Offshore wind farms can generate more consistent power",
                "Wind power is one of the most cost-effective renewable sources",
                "Modern wind turbines can power thousands of homes"
            ),
            "hydro" to listOf(
                "Hydroelectric power uses flowing water to generate electricity",
                "Pumped storage systems help balance grid demand",
                "Small-scale hydro can power remote communities",
                "Hydropower is one of the most reliable renewable sources"
            ),
            "biomass" to listOf(
                "Biomass energy comes from organic materials like wood and crop waste",
                "Biogas can be produced from agricultural and food waste",
                "Biomass can provide baseload power generation",
                "Modern biomass plants have advanced emission controls"
            ),
            "geothermal" to listOf(
                "Geothermal energy taps into Earth's internal heat",
                "Heat pumps can use shallow geothermal energy for heating/cooling",
                "Geothermal power plants provide consistent power output",
                "Enhanced geothermal systems can work in most locations"
            ),
            "storage" to listOf(
                "Energy storage is crucial for renewable energy systems",
                "Battery technology is rapidly advancing and costs are decreasing",
                "Pumped hydro is currently the largest form of energy storage",
                "Smart grids help manage energy storage and distribution"
            )
        )

        // Function to get backup response, can be called from other activities
        fun getBackupResponse(query: String): String {
            val normalizedQuery = query.lowercase().trim()
            
            // Check for exact matches first
            sharedKnowledgeBase.forEach { (key, responses) ->
                if (normalizedQuery.contains(key)) {
                    return responses.random()
                }
            }

            // If no exact match, try fuzzy matching
            val bestMatch = findBestMatch(normalizedQuery)
            return bestMatch?.let { sharedKnowledgeBase[it]?.random() }
                ?: getDefaultResponse()
        }

        private fun findBestMatch(query: String): String? {
            return sharedKnowledgeBase.keys.maxByOrNull { key ->
                calculateSimilarity(query, key)
            }
        }

        private fun calculateSimilarity(s1: String, s2: String): Double {
            val longer = if (s1.length > s2.length) s1 else s2
            val shorter = if (s1.length > s2.length) s2 else s1
            
            if (longer.contains(shorter)) return 1.0
            
            val longerLength = longer.length
            return (longerLength - calculateLevenshteinDistance(longer, shorter)) / longerLength.toDouble()
        }

        private fun calculateLevenshteinDistance(s1: String, s2: String): Int {
            val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
            
            for (i in 0..s1.length) dp[i][0] = i
            for (j in 0..s2.length) dp[0][j] = j
            
            for (i in 1..s1.length) {
                for (j in 1..s2.length) {
                    dp[i][j] = minOf(
                        dp[i-1][j] + 1,
                        dp[i][j-1] + 1,
                        dp[i-1][j-1] + if (s1[i-1] == s2[j-1]) 0 else 1
                    )
                }
            }
            
            return dp[s1.length][s2.length]
        }

        private fun getDefaultResponse(): String {
            return "I can provide information about:\n" +
                   "• Solar energy\n" +
                   "• Wind power\n" +
                   "• Hydroelectric power\n" +
                   "• Biomass energy\n" +
                   "• Geothermal energy\n" +
                   "• Energy storage\n\n" +
                   "What would you like to know more about?"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gen_ai)

        initializeViews()
        setupRecyclerView()
        setupClickListeners()
        initializeKnowledgeBase()
        addWelcomeMessage()
        
        // Handle incoming queries from ChatActivity if any
        handleIncomingQuery()
    }

    private fun handleIncomingQuery() {
        intent.getStringExtra("query")?.let { query ->
            messageInput.setText(query)
            handleUserMessage()
        }
    }

    private fun initializeViews() {
        recyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        suggestionChipGroup = findViewById(R.id.suggestionChipGroup)
        statusChip = findViewById(R.id.statusChip)
        progressIndicator = findViewById(R.id.progressIndicator)
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@GenAIActivity).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }

        // Add scroll listener to auto-scroll to bottom on new messages
        chatAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            }
        })
    }

    private fun setupClickListeners() {
        sendButton.setOnClickListener {
            handleUserMessage()
        }

        messageInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                handleUserMessage()
                true
            } else {
                false
            }
        }
    }

    private fun initializeKnowledgeBase() {
        knowledgeBase = RenewableEnergyKnowledge(this)
    }

    private fun addWelcomeMessage() {
        addMessage(
            "Hello! I'm your Advanced Renewable Energy Assistant. I can help you with:\n" +
            "• Detailed information about renewable energy sources\n" +
            "• Technical specifications and comparisons\n" +
            "• Implementation guidance and best practices\n" +
            "• Latest trends and innovations\n\n" +
            "What would you like to know about renewable energy?",
            false
        )
        addSuggestionChips(listOf(
            "Tell me about solar energy",
            "How does wind power work?",
            "Compare renewable sources"
        ))
    }

    private fun handleUserMessage() {
        val userMessage = messageInput.text?.toString()?.trim() ?: return
        if (userMessage.isEmpty()) return

        messageInput.text = null
        suggestionChipGroup.removeAllViews()
        setProcessingState(true)
        addMessage(userMessage, true)

        lifecycleScope.launch(Dispatchers.IO) {
            // Try to get response from knowledge base first
            var response = knowledgeBase.findResponse(userMessage)
            
            // If no response or response is a default/fallback, try backup knowledge
            if (response.contains("I'm not quite sure") || response.contains("I don't have specific information")) {
                response = getBackupResponse(userMessage)
            }
            
            withContext(Dispatchers.Main) {
                addMessage(response, false)
                setProcessingState(false)
                addContextualSuggestions(userMessage, response)
            }
        }
    }

    private fun setProcessingState(isProcessing: Boolean) {
        progressIndicator.visibility = if (isProcessing) View.VISIBLE else View.GONE
        messageInput.isEnabled = !isProcessing
        sendButton.isEnabled = !isProcessing
        statusChip.text = if (isProcessing) "Processing..." else "Online"
    }

    private fun addMessage(message: String, isUser: Boolean) {
        messages.add(ChatMessage(message = message, isUser = isUser))
        chatAdapter.submitList(messages.toList())
    }

    private fun addSuggestionChips(suggestions: List<String>) {
        suggestionChipGroup.removeAllViews()
        suggestions.forEach { suggestion ->
            val chip = Chip(this).apply {
                text = suggestion
                isCheckable = false
                setOnClickListener {
                    messageInput.setText(suggestion)
                    handleUserMessage()
                }
            }
            suggestionChipGroup.addView(chip)
        }
    }

    private fun addContextualSuggestions(query: String, response: String) {
        val suggestions = when {
            query.contains("solar", ignoreCase = true) -> listOf(
                "Solar panel efficiency",
                "Solar installation cost",
                "Solar vs Wind energy"
            )
            query.contains("wind", ignoreCase = true) -> listOf(
                "Offshore wind farms",
                "Wind turbine types",
                "Wind power storage"
            )
            query.contains("hydro", ignoreCase = true) -> listOf(
                "Hydroelectric dam impact",
                "Small-scale hydro",
                "Pumped storage"
            )
            query.contains("biomass", ignoreCase = true) -> listOf(
                "Biomass fuel types",
                "Biomass efficiency",
                "Environmental impact"
            )
            query.contains("geothermal", ignoreCase = true) -> listOf(
                "Geothermal heat pumps",
                "Geothermal locations",
                "Installation costs"
            )
            query.contains("storage", ignoreCase = true) -> listOf(
                "Battery technologies",
                "Grid integration",
                "Cost comparison"
            )
            else -> listOf(
                "Compare energy sources",
                "Environmental benefits",
                "Implementation costs"
            )
        }
        addSuggestionChips(suggestions)
    }
} 