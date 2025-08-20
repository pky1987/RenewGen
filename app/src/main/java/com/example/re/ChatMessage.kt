package com.example.re

data class ChatMessage(
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isUser: Boolean,
    val id: String = java.util.UUID.randomUUID().toString()
) 