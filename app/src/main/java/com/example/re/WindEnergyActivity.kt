package com.example.re

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.databinding.ActivitySolarEnergyBinding
import com.example.re.activities.WindEfficiencyActivity
import com.example.re.activities.WindImpactActivity
import com.example.re.activities.WindOverviewActivity
import com.example.re.activities.WindProductionActivity
import com.example.re.activities.WindStatisticsActivity
import com.example.re.activities.WindTechnologyActivity
import com.example.re.databinding.ActivityWindEnergyBinding

class WindEnergyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWindEnergyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWindEnergyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cd1.setOnClickListener {
            startActivity(Intent(this, WindOverviewActivity::class.java))
        }
        binding.cd2.setOnClickListener {
            startActivity(Intent(this, WindProductionActivity::class.java))
        }
        binding.cd3.setOnClickListener {
            startActivity(Intent(this, WindEfficiencyActivity::class.java))
        }
        binding.cd4.setOnClickListener {
            startActivity(Intent(this, WindTechnologyActivity::class.java))
        }
        binding.cd5.setOnClickListener {
            startActivity(Intent(this, WindStatisticsActivity::class.java))
        }
        binding.cd6.setOnClickListener {
            startActivity(Intent(this, WindImpactActivity::class.java))
        }
    }
}