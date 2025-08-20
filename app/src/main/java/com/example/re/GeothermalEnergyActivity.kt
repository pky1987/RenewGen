package com.example.re

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.activities.GeothermalEfficiencyActivity
import com.example.re.activities.GeothermalImpactActivity
import com.example.re.databinding.ActivitySolarEnergyBinding
import com.example.re.activities.GeothermalOverviewActivity
import com.example.re.activities.GeothermalProductionActivity
import com.example.re.activities.GeothermalStatisticsActivity
import com.example.re.activities.GeothermalTechnologyActivity
import com.example.re.databinding.ActivityGeothermalEnergyBinding

class GeothermalEnergyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGeothermalEnergyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeothermalEnergyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cd1.setOnClickListener {
            startActivity(Intent(this, GeothermalOverviewActivity::class.java))
        }
        binding.cd2.setOnClickListener {
            startActivity(Intent(this, GeothermalProductionActivity::class.java))
        }
        binding.cd3.setOnClickListener {
            startActivity(Intent(this, GeothermalEfficiencyActivity::class.java))
        }
        binding.cd4.setOnClickListener {
            startActivity(Intent(this, GeothermalTechnologyActivity::class.java))
        }
        binding.cd5.setOnClickListener {
            startActivity(Intent(this, GeothermalStatisticsActivity::class.java))
        }
        binding.cd6.setOnClickListener {
            startActivity(Intent(this, GeothermalImpactActivity::class.java))
        }
    }
}