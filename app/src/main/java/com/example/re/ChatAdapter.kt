package com.example.re

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class ChatAdapter : ListAdapter<ChatMessage, ChatAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageCard: MaterialCardView = itemView.findViewById(R.id.messageCard)
        private val messageText: TextView = itemView.findViewById(R.id.messageText)
        private val timestampText: TextView = itemView.findViewById(R.id.timestampText)

        fun bind(message: ChatMessage) {
            messageText.text = message.message
            
            // Set different background colors for user and assistant messages
            messageCard.setCardBackgroundColor(
                itemView.context.getColor(
                    if (message.isUser) R.color.primary_light else R.color.background_light
                )
            )
            
            // Set text color based on background
            messageText.setTextColor(
                itemView.context.getColor(
                    if (message.isUser) R.color.white else R.color.text_primary
                )
            )

            // Align messages to different sides
            val params = messageCard.layoutParams as ViewGroup.MarginLayoutParams
            if (message.isUser) {
                params.marginStart = 64
                params.marginEnd = 8
            } else {
                params.marginStart = 8
                params.marginEnd = 64
            }
            messageCard.layoutParams = params

            // Set timestamp if available
            if (message.timestamp != null) {
                timestampText.text = formatTimestamp(message.timestamp)
                timestampText.visibility = View.VISIBLE
            } else {
                timestampText.visibility = View.GONE
            }
        }

        private fun formatTimestamp(timestamp: Long): String {
            val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
            return sdf.format(java.util.Date(timestamp))
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.message == newItem.message && 
                   oldItem.isUser == newItem.isUser &&
                   oldItem.timestamp == newItem.timestamp
        }
    }
}