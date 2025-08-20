package com.example.re.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

abstract class BaseChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    protected val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    protected var chartRect = RectF()
    protected var chartPadding = 50f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateChartRect()
    }

    private fun updateChartRect() {
        val width = width.toFloat()
        val height = height.toFloat()
        chartRect.set(
            chartPadding,
            chartPadding,
            width - chartPadding,
            height - chartPadding
        )
    }

    protected fun drawCenteredText(canvas: Canvas, text: String, x: Float, y: Float, textSize: Float) {
        paint.textSize = textSize
        canvas.drawText(text, x, y, paint)
    }

    protected fun formatValue(value: Float): String {
        return when {
            value >= 1_000_000 -> String.format("%.1fM", value / 1_000_000)
            value >= 1_000 -> String.format("%.1fK", value / 1_000)
            else -> String.format("%.1f", value)
        }
    }

    protected fun getColorForIndex(index: Int): Int {
        val colors = listOf(
            Color.rgb(255, 165, 0),   // Orange
            Color.rgb(0, 150, 255),   // Blue
            Color.rgb(34, 139, 34),   // Green
            Color.rgb(255, 69, 0),    // Red-Orange
            Color.rgb(128, 0, 128),   // Purple
            Color.rgb(0, 128, 128)    // Teal
        )
        return colors[index % colors.size]
    }

    data class DataPoint(
        val x: Float,
        val y: Float,
        val label: String = ""
    )

    protected fun calculateYAxisSteps(maxValue: Float): List<Float> {
        val step = when {
            maxValue > 1000 -> 500f
            maxValue > 100 -> 50f
            maxValue > 10 -> 5f
            else -> 1f
        }
        val steps = mutableListOf<Float>()
        var current = 0f
        while (current <= maxValue) {
            steps.add(current)
            current += step
        }
        if (steps.last() < maxValue) {
            steps.add(current)
        }
        return steps
    }
} 