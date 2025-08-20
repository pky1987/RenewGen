package com.example.re

import android.content.Context
import android.net.Uri

class UserProfileManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("UserProfilePrefs", Context.MODE_PRIVATE)

    fun saveUserProfile(name: String, email: String, phone: String, profileImageUri: Uri?) {
        with(sharedPreferences.edit()) {
            putString("name", name)
            putString("email", email)
            putString("phone", phone)
            profileImageUri?.let { uri ->
                putString("profileImageUri", uri.toString())
            }
            apply()
        }
    }

    fun getUserProfile(): UserProfile {
        val name = sharedPreferences.getString("name", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""
        val phone = sharedPreferences.getString("phone", "") ?: ""
        val profileImageUri = sharedPreferences.getString("profileImageUri", null)?.let { Uri.parse(it) }

        return UserProfile(name, email, phone, profileImageUri)
    }
}

