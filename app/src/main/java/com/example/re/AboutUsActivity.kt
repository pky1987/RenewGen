package com.example.re

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AboutUsActivity : AppCompatActivity() {

    private val handler = Handler()
    private var currentIndex = 0
    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AboutUsAdapter
    private val texts = listOf("Prakash Yadav", "Mrs. Nafisa Ansari", "Mr. Sachin Tiwari")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        textView = findViewById(R.id.txt)
        recyclerView = findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val renewableEnergyText = getString(R.string.renewable_energy_guide)
        adapter = AboutUsAdapter(renewableEnergyText) // Passing the full text
        recyclerView.adapter = adapter

        startChangingText()
    }

    private fun startChangingText() {
        handler.post(object : Runnable {
            override fun run() {
                textView.text = texts[currentIndex]
                currentIndex = (currentIndex + 1) % texts.size
                handler.postDelayed(this, 2000)
            }
        })
    }

    private fun getGuideSections(): List<String> {
        val guideString = getString(R.string.renewable_energy_guide)
        return guideString.split("\n\n")
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
