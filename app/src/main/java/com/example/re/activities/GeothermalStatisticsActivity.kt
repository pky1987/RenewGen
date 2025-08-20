package com.example.re.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.example.re.views.BaseChartView.DataPoint
import com.example.re.views.BarChartView
import com.example.re.views.LineChartView
import com.example.re.views.PieChartView

class GeothermalStatisticsActivity : AppCompatActivity() {

    private lateinit var capacityChart: LineChartView
    private lateinit var plantTypesChart: PieChartView
    private lateinit var productionChart: BarChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geothermal_statistics)

        capacityChart = findViewById(R.id.capacityChart)
        plantTypesChart = findViewById(R.id.plantTypesChart)
        productionChart = findViewById(R.id.productionChart)

        loadData()
    }

    private fun loadData() {
        loadCapacityData()
        loadPlantTypesData()
        loadProductionData()
    }

    private fun loadCapacityData() {
        // Global geothermal capacity data (GW) from 2015-2022
        val capacityData = listOf(
            DataPoint(2015f, 12.9f),
            DataPoint(2016f, 13.3f),
            DataPoint(2017f, 13.8f),
            DataPoint(2018f, 14.3f),
            DataPoint(2019f, 14.9f),
            DataPoint(2020f, 15.6f),
            DataPoint(2021f, 16.2f),
            DataPoint(2022f, 16.8f)
        )
        capacityChart.setData(capacityData, "Global Geothermal Capacity (GW)")
    }

    private fun loadPlantTypesData() {
        // Geothermal plant types distribution
        val plantTypesData = listOf(
            DataPoint(0f, 45f, "Flash Steam"),
            DataPoint(1f, 30f, "Dry Steam"),
            DataPoint(2f, 15f, "Binary Cycle"),
            DataPoint(3f, 10f, "Combined Cycle")
        )
        plantTypesChart.setData(plantTypesData, "Geothermal Plant Types")
    }

    private fun loadProductionData() {
        // Annual geothermal production (TWh) from 2017-2022
        val productionData = listOf(
            DataPoint(2017f, 85.3f, "2017"),
            DataPoint(2018f, 89.8f, "2018"),
            DataPoint(2019f, 94.2f, "2019"),
            DataPoint(2020f, 97.7f, "2020"),
            DataPoint(2021f, 102.5f, "2021"),
            DataPoint(2022f, 107.8f, "2022")
        )
        productionChart.setData(productionData, "Annual Geothermal Production (TWh)")
    }
}
