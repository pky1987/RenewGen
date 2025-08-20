package com.example.re

import android.net.Uri

data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    val profileImageUri: Uri?
)