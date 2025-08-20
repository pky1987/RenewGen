package com.example.re.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.example.re.views.BaseChartView.DataPoint
import com.example.re.views.BarChartView
import com.example.re.views.LineChartView
import com.example.re.views.PieChartView

class BiomassStatisticsActivity : AppCompatActivity() {

    private lateinit var capacityChart: LineChartView
    private lateinit var sourcesChart: PieChartView
    private lateinit var productionChart: BarChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biomass_statistics)

        capacityChart = findViewById(R.id.capacityChart)
        sourcesChart = findViewById(R.id.sourcesChart)
        productionChart = findViewById(R.id.productionChart)

        loadData()
    }

    private fun loadData() {
        loadCapacityData()
        loadSourcesData()
        loadProductionData()
    }

    private fun loadCapacityData() {
        // Global biomass capacity data (GW) from 2015-2022
        val capacityData = listOf(
            DataPoint(2015f, 106f),
            DataPoint(2016f, 112f),
            DataPoint(2017f, 118f),
            DataPoint(2018f, 124f),
            DataPoint(2019f, 132f),
            DataPoint(2020f, 138f),
            DataPoint(2021f, 143f),
            DataPoint(2022f, 148f)
        )
        capacityChart.setData(capacityData, "Global Biomass Capacity (GW)")
    }

    private fun loadSourcesData() {
        // Biomass sources distribution
        val sourcesData = listOf(
            DataPoint(0f, 45f, "Wood Biomass"),
            DataPoint(1f, 25f, "Agricultural Waste"),
            DataPoint(2f, 20f, "Municipal Waste"),
            DataPoint(3f, 10f, "Biogas")
        )
        sourcesChart.setData(sourcesData, "Biomass Sources Distribution")
    }

    private fun loadProductionData() {
        // Annual biomass production (TWh) from 2017-2022
        val productionData = listOf(
            DataPoint(2017f, 495f, "2017"),
            DataPoint(2018f, 515f, "2018"),
            DataPoint(2019f, 532f, "2019"),
            DataPoint(2020f, 548f, "2020"),
            DataPoint(2021f, 565f, "2021"),
            DataPoint(2022f, 580f, "2022")
        )
        productionChart.setData(productionData, "Annual Biomass Production (TWh)")
    }
}

