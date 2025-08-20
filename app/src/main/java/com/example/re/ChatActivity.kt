package com.example.re

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatActivity : AppCompatActivity() {
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var knowledgeBase: RenewableEnergyKnowledge
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: TextInputEditText
    private lateinit var sendButton: MaterialButton
    private val messages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize views
        recyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)

        // Initialize knowledge base
        knowledgeBase = RenewableEnergyKnowledge(this)

        // Setup RecyclerView
        chatAdapter = ChatAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }

        // Add welcome message
        addMessage(
            "Hello! I'm your Renewable Energy Assistant. Feel free to ask me anything about renewable energy sources, sustainability, or clean power generation!",
            false
        )

        // Setup click listeners
        sendButton.setOnClickListener {
            handleUserMessage()
        }

        // Handle keyboard send action
        messageInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                handleUserMessage()
                true
            } else {
                false
            }
        }

        // Add scroll listener to auto-scroll to bottom on new messages
        chatAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            }
        })
    }

    private fun handleUserMessage() {
        val userMessage = messageInput.text?.toString()?.trim() ?: return
        if (userMessage.isEmpty()) return

        // Clear input
        messageInput.text = null

        // Add user message
        addMessage(userMessage, true)

        // Process response in background
        lifecycleScope.launch(Dispatchers.IO) {
            val response = knowledgeBase.findResponse(userMessage)
            
            // Switch to main thread to update UI
            withContext(Dispatchers.Main) {
                addMessage(response, false)
            }
        }
    }

    private fun addMessage(message: String, isUser: Boolean) {
        messages.add(ChatMessage(message = message, isUser = isUser))
        chatAdapter.submitList(messages.toList())
    }
}