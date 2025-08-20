package com.example.re

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class New_Post : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var editTextAuthor: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextContent = findViewById(R.id.editTextContent)
        editTextAuthor = findViewById(R.id.editTextAuthor)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        db = DB(this)

        buttonSubmit.setOnClickListener {
            submitPost()
        }
    }

    private fun submitPost() {
        val title = editTextTitle.text.toString()
        val content = editTextContent.text.toString()
        val author = editTextAuthor.text.toString()
        val createdAt = Date()

        if (BadWord.containsBadWords(title) || BadWord.containsBadWords(content) || BadWord.containsBadWords(author)) {
            Toast.makeText(this, "Inappropriate content detected, post not submitted.", Toast.LENGTH_SHORT).show()
            return
        }

        if (title.isEmpty() || content.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        } else {
            val post = Post(0, title, content, author, createdAt)
            db.insertPost(post)
            finish()
        }
    }
}
