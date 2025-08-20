package com.example.re

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DeepSeekService {
    @POST("chat/completions")
    suspend fun generateResponse(
        @Body request: ChatRequest
    ): Response<ChatResponse>
}

data class ChatRequest(
    val messages: List<Message>,
    val temperature: Double = 0.7,
    val max_tokens: Int = 800
)

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)