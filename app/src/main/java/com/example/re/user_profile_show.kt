package com.example.re

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class user_profile_show : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var editButton: Button

    private lateinit var userProfileManager: UserProfileManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile_show)

        // Initialize views
        profileImageView = findViewById(R.id.profileImageView)
        nameTextView = findViewById(R.id.nameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        editButton = findViewById(R.id.editButton)

        // Initialize UserProfileManager
        userProfileManager = UserProfileManager(this)

        // Load user profile data
        loadUserProfile()

        // Set up edit button listener
        editButton.setOnClickListener {
            val intent = Intent(this, profile::class.java) // Replace Profile::class.java with your actual edit activity class
            startActivity(intent)
            finish()
            finish() // Placeholder; replace with navigation if needed
        }
    }

    private fun loadUserProfile() {
        val userProfile = userProfileManager.getUserProfile()

        nameTextView.text = userProfile.name
        emailTextView.text = userProfile.email
        phoneTextView.text = userProfile.phone
        userProfile.profileImageUri?.let { uri ->
            profileImageView.setImageURI(uri)
        } ?: run {
            profileImageView.setImageResource(R.drawable.solarenergy) // Fallback image
        }
    }
}
