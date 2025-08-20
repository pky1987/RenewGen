package com.example.re

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.re.databinding.ActivityInvestmentBinding

class InvestmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInvestmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.yesButton.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://gggi.org/renewable-energy-is-now-a-commercially-attractive-investment-opportunity/"))
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