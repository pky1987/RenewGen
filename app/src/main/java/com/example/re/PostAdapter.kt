package com.example.re

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private var postList: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.textViewTitle.text = post.title
        holder.textViewContent.text = post.content
        holder.textViewAuthor.text = post.author
        holder.textViewCreatedAt.text = "Posted on ${post.createdAt}"

        val isLiked = post.isLiked
        updateLikeButton(holder.buttonLike, isLiked)

        holder.buttonLike.setOnClickListener {
            val newLikeState = !isLiked
            post.isLiked = newLikeState
            updateLikeButton(holder.buttonLike, newLikeState)

            val currentState = if (newLikeState) "Liked" else "Unliked"
            Toast.makeText(holder.context, "${position + 1}.${post.title}  is $currentState", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun updatePosts(newPosts: List<Post>) {
        postList = newPosts
        notifyDataSetChanged()
    }

    private fun updateLikeButton(buttonLike: ImageButton, isLiked: Boolean) {
        val drawableRes = if (isLiked) {
            R.drawable.like
        } else {
            R.drawable.unlike
        }
        buttonLike.setImageResource(drawableRes)
    }

    inner class PostViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewContent: TextView = itemView.findViewById(R.id.textViewContent)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        val textViewCreatedAt: TextView = itemView.findViewById(R.id.textViewCreatedAt)
        val buttonLike: ImageButton = itemView.findViewById(R.id.buttonLike)
    }
}
