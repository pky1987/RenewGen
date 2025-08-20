package com.example.re

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.activities.HydroEfficiencyActivity
import com.example.re.activities.HydroImpactActivity
import com.example.re.activities.HydroOverviewActivity
import com.example.re.activities.HydroProductionActivity
import com.example.re.activities.HydroStatisticsActivity
import com.example.re.activities.HydroTechnologyActivity
import com.example.re.databinding.ActivityHydroEnergyBinding

class HydroEnergyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHydroEnergyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHydroEnergyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cd1.setOnClickListener {
            startActivity(Intent(this, HydroOverviewActivity::class.java))
        }
        binding.cd2.setOnClickListener {
            startActivity(Intent(this, HydroProductionActivity::class.java))
        }
        binding.cd3.setOnClickListener {
            startActivity(Intent(this, HydroEfficiencyActivity::class.java))
        }
        binding.cd4.setOnClickListener {
            startActivity(Intent(this, HydroTechnologyActivity::class.java))
        }
        binding.cd5.setOnClickListener {
            startActivity(Intent(this, HydroStatisticsActivity::class.java))
        }
        binding.cd6.setOnClickListener {
            startActivity(Intent(this, HydroImpactActivity::class.java))
        }
    }
}