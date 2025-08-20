package com.example.re.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.example.re.views.BaseChartView.DataPoint
import com.example.re.views.BarChartView
import com.example.re.views.LineChartView
import com.example.re.views.PieChartView

class TidalStatisticsActivity : AppCompatActivity() {

    private lateinit var capacityChart: LineChartView
    private lateinit var technologyChart: PieChartView
    private lateinit var productionChart: BarChartView
    private lateinit var efficiencyChart: LineChartView
    private lateinit var potentialChart: BarChartView
    private lateinit var investmentChart: PieChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tidal_statistics)

        capacityChart = findViewById(R.id.capacityChart)
        technologyChart = findViewById(R.id.technologyChart)
        productionChart = findViewById(R.id.productionChart)
        efficiencyChart = findViewById(R.id.efficiencyChart)
        potentialChart = findViewById(R.id.potentialChart)
        investmentChart = findViewById(R.id.investmentChart)

        loadData()
    }

    private fun loadData() {
        loadCapacityData()
        loadTechnologyData()
        loadProductionData()
        loadEfficiencyData()
        loadPotentialData()
        loadInvestmentData()
    }

    private fun loadCapacityData() {
        // Global tidal energy capacity data (MW) from 2015-2022
        val capacityData = listOf(
            DataPoint(2015f, 254f),
            DataPoint(2016f, 273f),
            DataPoint(2017f, 285f),
            DataPoint(2018f, 298f),
            DataPoint(2019f, 315f),
            DataPoint(2020f, 332f),
            DataPoint(2021f, 348f),
            DataPoint(2022f, 365f)
        )
        capacityChart.setData(capacityData, "Global Tidal Energy Capacity (MW)")
    }

    private fun loadTechnologyData() {
        // Tidal energy technology distribution
        val technologyData = listOf(
            DataPoint(0f, 45f, "Tidal Stream"),
            DataPoint(1f, 30f, "Tidal Barrage"),
            DataPoint(2f, 15f, "Dynamic Tidal Power"),
            DataPoint(3f, 10f, "Tidal Lagoon")
        )
        technologyChart.setData(technologyData, "Tidal Energy Technologies")
    }

    private fun loadProductionData() {
        // Annual tidal energy production (GWh) from 2017-2022
        val productionData = listOf(
            DataPoint(2017f, 495f, "2017"),
            DataPoint(2018f, 532f, "2018"),
            DataPoint(2019f, 578f, "2019"),
            DataPoint(2020f, 615f, "2020"),
            DataPoint(2021f, 652f, "2021"),
            DataPoint(2022f, 698f, "2022")
        )
        productionChart.setData(productionData, "Annual Tidal Energy Production (GWh)")
    }

    private fun loadEfficiencyData() {
        // Tidal energy efficiency trends (%) from 2017-2022
        val efficiencyData = listOf(
            DataPoint(2017f, 22.5f),
            DataPoint(2018f, 24.2f),
            DataPoint(2019f, 25.8f),
            DataPoint(2020f, 27.3f),
            DataPoint(2021f, 28.9f),
            DataPoint(2022f, 30.5f)
        )
        efficiencyChart.setData(efficiencyData, "Tidal Energy Efficiency (%)")
    }

    private fun loadPotentialData() {
        // Global tidal energy potential by region (TWh/year)
        val potentialData = listOf(
            DataPoint(0f, 380f, "East Asia"),
            DataPoint(1f, 250f, "Europe"),
            DataPoint(2f, 190f, "North America"),
            DataPoint(3f, 115f, "South America"),
            DataPoint(4f, 85f, "Oceania")
        )
        potentialChart.setData(potentialData, "Regional Tidal Energy Potential (TWh/year)")
    }

    private fun loadInvestmentData() {
        // Investment distribution by sector
        val investmentData = listOf(
            DataPoint(0f, 35f, "Government"),
            DataPoint(1f, 30f, "Private Sector"),
            DataPoint(2f, 20f, "Research Institutions"),
            DataPoint(3f, 15f, "International Aid")
        )
        investmentChart.setData(investmentData, "Investment Distribution")
    }
}
