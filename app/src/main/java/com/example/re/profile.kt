package com.example.re

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class profile : AppCompatActivity() {
    private lateinit var profileImageView: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var selectImageButton: Button
    private lateinit var saveProfileButton: Button

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImageView = findViewById(R.id.profileImageView)
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        selectImageButton = findViewById(R.id.selectImageButton)
        saveProfileButton = findViewById(R.id.saveProfileButton)

        // Handle image selection
        selectImageButton.setOnClickListener {
            openImagePicker()
        }

        // Handle saving profile information
        saveProfileButton.setOnClickListener {
            saveProfile()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedImageUri = data?.data
            profileImageView.setImageURI(selectedImageUri) // Set selected image to ImageView
        } else {
            Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveProfile() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Here you can implement the logic to save the profile information, e.g., to a database or shared preferences
        // Example using SharedPreferences:
        val sharedPreferences = getSharedPreferences("UserProfilePrefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("name", name)
            putString("email", email)
            putString("phone", phone)
            selectedImageUri?.let { uri ->
                putString("profileImageUri", uri.toString())
            }
            apply()
        }

        Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show()
        finish() // Optionally close the activity
    }
}
