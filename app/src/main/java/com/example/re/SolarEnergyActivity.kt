package com.example.re

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.databinding.ActivitySolarEnergyBinding
import com.example.re.activities.SolarEfficiencyActivity
import com.example.re.activities.SolarImpactActivity
import com.example.re.activities.SolarOverviewActivity
import com.example.re.activities.SolarProductionActivity
import com.example.re.activities.SolarStatisticsActivity
import com.example.re.activities.SolarTechnologyActivity

class SolarEnergyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySolarEnergyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolarEnergyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cd1.setOnClickListener {
            startActivity(Intent(this, SolarOverviewActivity::class.java))
        }
        binding.cd2.setOnClickListener {
            startActivity(Intent(this, SolarProductionActivity::class.java))
        }
        binding.cd3.setOnClickListener {
            startActivity(Intent(this, SolarEfficiencyActivity::class.java))
        }
        binding.cd4.setOnClickListener {
            startActivity(Intent(this, SolarTechnologyActivity::class.java))
        }
        binding.cd5.setOnClickListener {
            startActivity(Intent(this, SolarStatisticsActivity::class.java))
        }
        binding.cd6.setOnClickListener {
            startActivity(Intent(this, SolarImpactActivity::class.java))
        }
    }
}