package com.example.re

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class LanguageActivity : AppCompatActivity() {

    private lateinit var languageRadioGroup: RadioGroup
    private lateinit var btnApplyLanguage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        languageRadioGroup = findViewById(R.id.languageRadioGroup)
        btnApplyLanguage = findViewById(R.id.btnApplyLanguage)

        updateSelectedLanguage()
        btnApplyLanguage.setOnClickListener { applySelectedLanguage() }
    }

    private fun updateSelectedLanguage() {
        when (App.getInstance().getSavedLanguage()) {
            "en" -> languageRadioGroup.check(R.id.radioEnglish)
            "hi" -> languageRadioGroup.check(R.id.radioHindi)
            "es" -> languageRadioGroup.check(R.id.radioSpanish)
            "fr" -> languageRadioGroup.check(R.id.radioFrench)
        }
    }

    private fun applySelectedLanguage() {
        val selectedLanguage = when (languageRadioGroup.checkedRadioButtonId) {
            R.id.radioEnglish -> "en"
            R.id.radioHindi -> "hi"
            R.id.radioSpanish -> "es"
            R.id.radioFrench -> "fr"
            else -> "en"
        }

        App.getInstance().saveLanguage(selectedLanguage)
        updateLocale(selectedLanguage)
        restartApp()
    }

    private fun updateLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun restartApp() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }
}