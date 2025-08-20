package com.example.re.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.example.re.views.BaseChartView.DataPoint
import com.example.re.views.BarChartView
import com.example.re.views.LineChartView
import com.example.re.views.PieChartView

class HydroStatisticsActivity : AppCompatActivity() {

    private lateinit var capacityChart: LineChartView
    private lateinit var plantTypesChart: PieChartView
    private lateinit var productionChart: BarChartView
    private lateinit var efficiencyChart: LineChartView
    private lateinit var environmentalImpactChart: BarChartView
    private lateinit var waterUsageChart: PieChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hydro_statistics)

        capacityChart = findViewById(R.id.capacityChart)
        plantTypesChart = findViewById(R.id.plantTypesChart)
        productionChart = findViewById(R.id.productionChart)
        efficiencyChart = findViewById(R.id.efficiencyChart)
        environmentalImpactChart = findViewById(R.id.environmentalImpactChart)
        waterUsageChart = findViewById(R.id.waterUsageChart)

        loadData()
    }

    private fun loadData() {
        loadCapacityData()
        loadPlantTypesData()
        loadProductionData()
        loadEfficiencyData()
        loadEnvironmentalImpactData()
        loadWaterUsageData()
    }

    private fun loadCapacityData() {
        // Global hydropower capacity data (GW) from 2015-2022
        val capacityData = listOf(
            DataPoint(2015f, 1212f),
            DataPoint(2016f, 1246f),
            DataPoint(2017f, 1270f),
            DataPoint(2018f, 1292f),
            DataPoint(2019f, 1308f),
            DataPoint(2020f, 1330f),
            DataPoint(2021f, 1360f),
            DataPoint(2022f, 1392f)
        )
        capacityChart.setData(capacityData, "Global Hydropower Capacity (GW)")
    }

    private fun loadPlantTypesData() {
        // Hydropower plant types distribution
        val plantTypesData = listOf(
            DataPoint(0f, 45f, "Large Scale"),
            DataPoint(1f, 25f, "Run-of-River"),
            DataPoint(2f, 20f, "Pumped Storage"),
            DataPoint(3f, 10f, "Small Scale")
        )
        plantTypesChart.setData(plantTypesData, "Hydropower Plant Types")
    }

    private fun loadProductionData() {
        // Annual hydropower production (TWh) from 2017-2022
        val productionData = listOf(
            DataPoint(2017f, 4185f, "2017"),
            DataPoint(2018f, 4239f, "2018"),
            DataPoint(2019f, 4306f, "2019"),
            DataPoint(2020f, 4370f, "2020"),
            DataPoint(2021f, 4432f, "2021"),
            DataPoint(2022f, 4512f, "2022")
        )
        productionChart.setData(productionData, "Annual Hydropower Production (TWh)")
    }

    private fun loadEfficiencyData() {
        // Hydropower plant efficiency trends (%) from 2017-2022
        val efficiencyData = listOf(
            DataPoint(2017f, 85.2f),
            DataPoint(2018f, 86.5f),
            DataPoint(2019f, 87.8f),
            DataPoint(2020f, 88.4f),
            DataPoint(2021f, 89.2f),
            DataPoint(2022f, 90.1f)
        )
        efficiencyChart.setData(efficiencyData, "Hydropower Plant Efficiency (%)")
    }

    private fun loadEnvironmentalImpactData() {
        // Environmental impact metrics (scale 1-10, lower is better)
        val impactData = listOf(
            DataPoint(0f, 3.2f, "CO2 Emissions"),
            DataPoint(1f, 5.8f, "Ecosystem Impact"),
            DataPoint(2f, 4.5f, "Land Use"),
            DataPoint(3f, 2.8f, "Air Quality"),
            DataPoint(4f, 6.2f, "Fish Migration")
        )
        environmentalImpactChart.setData(impactData, "Environmental Impact Assessment")
    }

    private fun loadWaterUsageData() {
        // Water usage distribution by purpose
        val waterUsageData = listOf(
            DataPoint(0f, 40f, "Power Generation"),
            DataPoint(1f, 25f, "Irrigation"),
            DataPoint(2f, 20f, "Flood Control"),
            DataPoint(3f, 10f, "Recreation"),
            DataPoint(4f, 5f, "Other Uses")
        )
        waterUsageChart.setData(waterUsageData, "Water Usage Distribution")
    }
}
