package com.example.re

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.activities.BiomassEfficiencyActivity
import com.example.re.activities.BiomassImpactActivity
import com.example.re.activities.BiomassOverviewActivity
import com.example.re.activities.BiomassProductionActivity
import com.example.re.databinding.ActivitySolarEnergyBinding
import com.example.re.activities.BiomassStatisticsActivity
import com.example.re.activities.BiomassTechnologyActivity
import com.example.re.databinding.ActivityBiomassEnergyBinding

class BiomassEnergyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBiomassEnergyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityBiomassEnergyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cd1.setOnClickListener {
            startActivity(Intent(this, BiomassOverviewActivity::class.java))
        }
        binding.cd2.setOnClickListener {
            startActivity(Intent(this, BiomassProductionActivity::class.java))
        }
        binding.cd3.setOnClickListener {
            startActivity(Intent(this, BiomassEfficiencyActivity::class.java))
        }
        binding.cd4.setOnClickListener {
            startActivity(Intent(this, BiomassTechnologyActivity::class.java))
        }
        binding.cd5.setOnClickListener {
            startActivity(Intent(this, BiomassStatisticsActivity::class.java))
        }
        binding.cd6.setOnClickListener {
            startActivity(Intent(this, BiomassImpactActivity::class.java))
        }
    }
}