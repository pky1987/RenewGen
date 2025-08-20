package com.example.re

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class FeedbackActivity : AppCompatActivity() {

    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var ratingBar: RatingBar
    private lateinit var commentsInput: TextInputEditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        // Initialize views
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        ratingBar = findViewById(R.id.ratingBar)
        commentsInput = findViewById(R.id.commentsInput)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            submitFeedback()
        }
    }

    private fun submitFeedback() {
        val name = nameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val rating = ratingBar.rating
        val comments = commentsInput.text.toString().trim()

        if (name.isEmpty()) {
            nameInput.error = getString(R.string.name_required)
            return
        }

        if (email.isEmpty()) {
            emailInput.error = getString(R.string.email_required)
            return
        }

        if (comments.isEmpty()) {
            commentsInput.error = getString(R.string.comments_required)
            return
        }

        sendFeedbackEmail(name, email, rating, comments)
    }

    private fun sendFeedbackEmail(name: String, email: String, rating: Float, comments: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf("renewable.support@example.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Renewable Energy App Feedback")
            putExtra(Intent.EXTRA_TEXT, """
                Name: $name
                Email: $email
                Rating: $rating/5
                Comments: 
                $comments
            """.trimIndent())
        }

        try {
            startActivity(intent)
            showToast(getString(R.string.feedback_thanks))
        } catch (e: Exception) {
            showToast(getString(R.string.no_email_client))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}