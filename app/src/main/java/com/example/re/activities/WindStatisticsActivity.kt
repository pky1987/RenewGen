package com.example.re.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.example.re.views.BaseChartView.DataPoint
import com.example.re.views.BarChartView
import com.example.re.views.LineChartView
import com.example.re.views.PieChartView

class WindStatisticsActivity : AppCompatActivity() {

    private lateinit var capacityChart: LineChartView
    private lateinit var turbineTypesChart: PieChartView
    private lateinit var productionChart: BarChartView
    private lateinit var efficiencyChart: LineChartView
    private lateinit var costChart: BarChartView
    private lateinit var locationChart: PieChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wind_statistics)

        capacityChart = findViewById(R.id.capacityChart)
        turbineTypesChart = findViewById(R.id.turbineTypesChart)
        productionChart = findViewById(R.id.productionChart)
        efficiencyChart = findViewById(R.id.efficiencyChart)
        costChart = findViewById(R.id.costChart)
        locationChart = findViewById(R.id.locationChart)

        loadData()
    }

    private fun loadData() {
        loadCapacityData()
        loadTurbineTypesData()
        loadProductionData()
        loadEfficiencyData()
        loadCostData()
        loadLocationData()
    }

    private fun loadCapacityData() {
        // Global wind capacity data (GW) from 2015-2022
        val capacityData = listOf(
            DataPoint(2015f, 433f),
            DataPoint(2016f, 487f),
            DataPoint(2017f, 539f),
            DataPoint(2018f, 591f),
            DataPoint(2019f, 651f),
            DataPoint(2020f, 743f),
            DataPoint(2021f, 837f),
            DataPoint(2022f, 907f)
        )
        capacityChart.setData(capacityData, "Global Wind Capacity (GW)")
    }

    private fun loadTurbineTypesData() {
        // Wind turbine types distribution
        val turbineTypesData = listOf(
            DataPoint(0f, 50f, "Horizontal Axis"),
            DataPoint(1f, 25f, "Offshore"),
            DataPoint(2f, 15f, "Vertical Axis"),
            DataPoint(3f, 10f, "Other Types")
        )
        turbineTypesChart.setData(turbineTypesData, "Wind Turbine Types")
    }

    private fun loadProductionData() {
        // Annual wind production (TWh) from 2017-2022
        val productionData = listOf(
            DataPoint(2017f, 1127f, "2017"),
            DataPoint(2018f, 1273f, "2018"),
            DataPoint(2019f, 1424f, "2019"),
            DataPoint(2020f, 1592f, "2020"),
            DataPoint(2021f, 1862f, "2021"),
            DataPoint(2022f, 2110f, "2022")
        )
        productionChart.setData(productionData, "Annual Wind Production (TWh)")
    }

    private fun loadEfficiencyData() {
        // Wind turbine efficiency trends (%) from 2017-2022
        val efficiencyData = listOf(
            DataPoint(2017f, 35.2f),
            DataPoint(2018f, 37.5f),
            DataPoint(2019f, 39.8f),
            DataPoint(2020f, 41.2f),
            DataPoint(2021f, 42.6f),
            DataPoint(2022f, 44.1f)
        )
        efficiencyChart.setData(efficiencyData, "Wind Turbine Efficiency (%)")
    }

    private fun loadCostData() {
        // Cost per kWh (cents) by installation type
        val costData = listOf(
            DataPoint(0f, 3.7f, "Onshore"),
            DataPoint(1f, 8.2f, "Offshore Fixed"),
            DataPoint(2f, 11.5f, "Offshore Floating"),
            DataPoint(3f, 9.8f, "Hybrid Systems")
        )
        costChart.setData(costData, "Wind Energy Cost (cents/kWh)")
    }

    private fun loadLocationData() {
        // Global installation locations
        val locationData = listOf(
            DataPoint(0f, 40f, "Asia Pacific"),
            DataPoint(1f, 30f, "Europe"),
            DataPoint(2f, 20f, "North America"),
            DataPoint(3f, 7f, "South America"),
            DataPoint(4f, 3f, "Africa")
        )
        locationChart.setData(locationData, "Global Installation Locations")
    }
}
