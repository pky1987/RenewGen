package com.example.re.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.example.re.views.BaseChartView.DataPoint
import com.example.re.views.BarChartView
import com.example.re.views.LineChartView
import com.example.re.views.PieChartView

class SolarStatisticsActivity : AppCompatActivity() {

    private lateinit var capacityChart: LineChartView
    private lateinit var technologyChart: PieChartView
    private lateinit var productionChart: BarChartView
    private lateinit var efficiencyChart: LineChartView
    private lateinit var costChart: BarChartView
    private lateinit var marketShareChart: PieChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_statistics)

        initializeViews()
        loadData()
    }

    private fun initializeViews() {
        capacityChart = findViewById(R.id.capacityChart)
        technologyChart = findViewById(R.id.technologyChart)
        productionChart = findViewById(R.id.productionChart)
        efficiencyChart = findViewById(R.id.efficiencyChart)
        costChart = findViewById(R.id.costChart)
        marketShareChart = findViewById(R.id.marketShareChart)
    }

    private fun loadData() {
        loadCapacityData()
        loadTechnologyData()
        loadProductionData()
        loadEfficiencyData()
        loadCostData()
        loadMarketShareData()
    }

    private fun loadCapacityData() {
        // Global solar capacity data (GW) from 2015-2022
        val capacityData = listOf(
            DataPoint(2015f, 227f),
            DataPoint(2016f, 303f),
            DataPoint(2017f, 405f),
            DataPoint(2018f, 512f),
            DataPoint(2019f, 586f),
            DataPoint(2020f, 714f),
            DataPoint(2021f, 849f),
            DataPoint(2022f, 1050f)
        )
        capacityChart.setData(capacityData, "Global Solar Capacity (GW)")
    }

    private fun loadTechnologyData() {
        // Solar technology distribution
        val technologyData = listOf(
            DataPoint(0f, 55f, "Crystalline Silicon"),
            DataPoint(1f, 20f, "Thin Film"),
            DataPoint(2f, 15f, "Concentrated Solar"),
            DataPoint(3f, 10f, "Other Technologies")
        )
        technologyChart.setData(technologyData, "Solar Technologies")
    }

    private fun loadProductionData() {
        // Annual solar production (TWh) from 2017-2022
        val productionData = listOf(
            DataPoint(2017f, 442f, "2017"),
            DataPoint(2018f, 585f, "2018"),
            DataPoint(2019f, 724f, "2019"),
            DataPoint(2020f, 855f, "2020"),
            DataPoint(2021f, 1032f, "2021"),
            DataPoint(2022f, 1289f, "2022")
        )
        productionChart.setData(productionData, "Annual Solar Production (TWh)")
    }

    private fun loadEfficiencyData() {
        // Solar panel efficiency trends (%) from 2017-2022
        val efficiencyData = listOf(
            DataPoint(2017f, 15.2f),
            DataPoint(2018f, 16.5f),
            DataPoint(2019f, 17.8f),
            DataPoint(2020f, 19.2f),
            DataPoint(2021f, 20.6f),
            DataPoint(2022f, 22.1f)
        )
        efficiencyChart.setData(efficiencyData, "Solar Panel Efficiency (%)")
    }

    private fun loadCostData() {
        // Cost per kWh (cents) by technology type
        val costData = listOf(
            DataPoint(0f, 5.2f, "Utility Scale"),
            DataPoint(1f, 7.8f, "Commercial"),
            DataPoint(2f, 10.5f, "Residential"),
            DataPoint(3f, 12.3f, "Off-grid")
        )
        costChart.setData(costData, "Solar Energy Cost (cents/kWh)")
    }

    private fun loadMarketShareData() {
        // Global market share by region
        val marketShareData = listOf(
            DataPoint(0f, 35f, "Asia Pacific"),
            DataPoint(1f, 25f, "Europe"),
            DataPoint(2f, 20f, "North America"),
            DataPoint(3f, 15f, "Rest of World"),
            DataPoint(4f, 5f, "Africa")
        )
        marketShareChart.setData(marketShareData, "Global Market Share by Region")
    }
}
