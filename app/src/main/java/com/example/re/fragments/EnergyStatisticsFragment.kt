package com.example.re.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.re.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter

abstract class EnergyStatisticsFragment : Fragment() {

    protected lateinit var capacityChart: LineChart
    protected lateinit var efficiencyChart: PieChart
    protected lateinit var productionChart: BarChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_energy_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupCharts(view)
        loadData()
    }

    private fun setupCharts(view: View) {
        capacityChart = view.findViewById(R.id.capacityChart)
        efficiencyChart = view.findViewById(R.id.efficiencyChart)
        productionChart = view.findViewById(R.id.productionChart)

        setupCapacityChart()
        setupEfficiencyChart()
        setupProductionChart()
    }

    private fun setupCapacityChart() {
        capacityChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            setPinchZoom(true)
            setDrawGridBackground(false)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                valueFormatter = YearFormatter()
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 0f
            }
            
            axisRight.isEnabled = false
            legend.isEnabled = true
        }
    }

    private fun setupEfficiencyChart() {
        efficiencyChart.apply {
            description.isEnabled = false
            setUsePercentValues(true)
            setDrawHoleEnabled(true)
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 58f
            transparentCircleRadius = 61f
            setDrawCenterText(true)
            rotationAngle = 0f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
        }
    }

    private fun setupProductionChart() {
        productionChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            setPinchZoom(false)
            setDrawBarShadow(false)
            setDrawGridBackground(false)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 0f
            }
            
            axisRight.isEnabled = false
            legend.isEnabled = true
        }
    }

    protected abstract fun loadData()

    inner class YearFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.toInt().toString()
        }
    }

    protected fun formatLargeNumber(number: Float): String {
        return when {
            number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000)
            number >= 1_000 -> String.format("%.1fK", number / 1_000)
            else -> String.format("%.0f", number)
        }
    }
} 