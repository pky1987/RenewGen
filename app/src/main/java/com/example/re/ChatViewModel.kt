package com.example.re

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.deepseek.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(DeepSeekService::class.java)

    fun sendMessage(text: String) {
        viewModelScope.launch {
            // Add user message
            val userMessage = Message(text, true)
            _messages.value = _messages.value?.plus(userMessage) ?: listOf(userMessage)

            try {
                // API call
                val response = service.generateResponse(
                    ChatRequest(messages = _messages.value!!.map {
                        Message(it.content, it.isUser)
                    })
                )

                if (response.isSuccessful) {
                    val aiMessage = response.body()?.choices?.first()?.message
                    aiMessage?.let {
                        _messages.value = _messages.value?.plus(it.copy(isUser = false))
                    }
                }
            } catch (e: Exception) {
                _messages.value = _messages.value?.plus(
                    Message("Error: ${e.message}", false)
                )
            }
        }
    }
}