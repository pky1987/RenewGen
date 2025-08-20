package com.example.re

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class gen_ai : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private lateinit var inputEditText: EditText
    private lateinit var knowledgeBase: RenewableEnergyKnowledge
    private val messages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gen_ai)

        setupViews()
        setupRecyclerView()
        initializeKnowledgeBase()
    }

    private fun setupViews() {
        recyclerView = findViewById(R.id.chatRecyclerView)
        inputEditText = findViewById(R.id.messageInput)
        
        findViewById<View>(R.id.sendButton).setOnClickListener {
            handleUserInput()
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        updateMessages(messages)
    }

    private fun initializeKnowledgeBase() {
        knowledgeBase = RenewableEnergyKnowledge(this)
    }

    private fun handleUserInput() {
        val userMessage = inputEditText.text.toString().trim()
        if (userMessage.isEmpty()) return

        // Add user message
        messages.add(ChatMessage(message = userMessage, isUser = true))
        updateMessages(messages)
        
        // Clear input
        inputEditText.text.clear()

        // Process in background
        CoroutineScope(Dispatchers.IO).launch {
            val response = knowledgeBase.findResponse(userMessage)
            
            withContext(Dispatchers.Main) {
                messages.add(ChatMessage(message = response, isUser = false))
                updateMessages(messages)
            }
        }
    }

    private fun updateMessages(newMessages: List<ChatMessage>) {
        adapter.submitList(newMessages.toList())
        recyclerView.scrollToPosition(newMessages.size - 1)
    }
}