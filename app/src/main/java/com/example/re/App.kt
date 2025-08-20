package com.example.re

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

class App : Application() {
    companion object {
        const val LANGUAGE_PREFS = "language_preferences"
        const val KEY_LANGUAGE = "selected_language"
        private lateinit var instance: App
        fun getInstance(): App = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        applySavedLanguage()
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }
    }

    private fun applySavedLanguage() {
        val locale = Locale(getSavedLanguage())
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun getSavedLanguage(): String {
        return getSharedPreferences(LANGUAGE_PREFS, Context.MODE_PRIVATE)
            .getString(KEY_LANGUAGE, Locale.getDefault().language) ?: "en"
    }

    fun saveLanguage(language: String) {
        getSharedPreferences(LANGUAGE_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LANGUAGE, language)
            .apply()
    }

    fun logout(completion: (Boolean) -> Unit) {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener { task ->
                FirebaseAuth.getInstance().signOut()
                completion(task.isSuccessful)
            }
    }
}