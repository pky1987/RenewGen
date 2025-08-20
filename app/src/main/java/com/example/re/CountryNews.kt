package com.example.re

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.re.databinding.ActivityCountryNewsBinding

class CountryNews : AppCompatActivity() {
    private lateinit var binding: ActivityCountryNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.yesButton.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mercomindia.com/"))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error opening link", Toast.LENGTH_SHORT).show()
            }
        }

        binding.noButton.setOnClickListener {
            binding.container.visibility = View.GONE
            binding.messageText.visibility = View.GONE
            binding.buttonContainer.visibility = View.GONE

            supportFragmentManager.commit {
                replace(R.id.container, HomeFragment())
                addToBackStack(null)
            }
        }
    }
}