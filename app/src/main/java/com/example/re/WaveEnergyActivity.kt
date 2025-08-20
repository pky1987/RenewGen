package com.example.re

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.databinding.ActivitySolarEnergyBinding
import com.example.re.activities.WaveEfficiencyActivity
import com.example.re.activities.WaveImpactActivity
import com.example.re.activities.WaveOverviewActivity
import com.example.re.activities.WaveProductionActivity
import com.example.re.activities.WaveStatisticsActivity
import com.example.re.activities.WaveTechnologyActivity
import com.example.re.databinding.ActivityWaveEnergyBinding

class WaveEnergyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWaveEnergyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaveEnergyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cd1.setOnClickListener {
            startActivity(Intent(this,  WaveOverviewActivity::class.java))
        }
        binding.cd2.setOnClickListener {
            startActivity(Intent(this,  WaveProductionActivity::class.java))
        }
        binding.cd3.setOnClickListener {
            startActivity(Intent(this,  WaveEfficiencyActivity::class.java))
        }
        binding.cd4.setOnClickListener {
            startActivity(Intent(this,  WaveTechnologyActivity::class.java))
        }
        binding.cd5.setOnClickListener {
            startActivity(Intent(this,  WaveStatisticsActivity::class.java))
        }
        binding.cd6.setOnClickListener {
            startActivity(Intent(this,  WaveImpactActivity::class.java))
        }
    }
}