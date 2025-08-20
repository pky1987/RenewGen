package com.example.re

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsListView: ListView

    private val settingsOptions = listOf(
        "Create Profile",
        "App Language",
        "Privacy",
        "Help",
        "About RenewGen"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingsListView = findViewById(R.id.settingsListView)

        val adapter = ArrayAdapter(this, R.layout.list_item_setting, R.id.settingName, settingsOptions)
        settingsListView.adapter = adapter

        settingsListView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> showProfileOptions()
                1 -> showLanguageOptions()
                2 -> showPrivacySettings()
                3 -> showHelp() // Help
                4 -> showAbout()
            }
        }
    }
    private fun showProfileOptions(){
        val intent= Intent(this,profile::class.java)
        startActivity(intent)
    }

    private fun showLanguageOptions() {
        val intent= Intent(this,LanguageActivity::class.java)
        startActivity(intent)
    }

    private fun showPrivacySettings() {
        val intent= Intent(this,PrivacyActivity::class.java)
        startActivity(intent)
    }


    private fun showHelp() {
        val intent= Intent(this,HelpActivity::class.java)
        startActivity(intent)
    }
    private fun showAbout(){
        val intent= Intent(this,AboutUsActivity::class.java)
        startActivity(intent)

    }
}