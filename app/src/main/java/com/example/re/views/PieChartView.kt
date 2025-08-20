package com.example.re.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseChartView(context, attrs, defStyleAttr) {

    private var dataPoints = listOf<DataPoint>()
    private var title = ""
    private var total = 0f
    private var selectedSlice = -1
    private val sliceSpacing = 5f
    private val highlightOffset = 30f

    private val rectF = RectF()
    private val centerPoint = PointF()
    private val sliceAngles = mutableListOf<Float>()

    fun setData(points: List<DataPoint>, chartTitle: String) {
        dataPoints = points
        title = chartTitle
        total = points.sumOf { it.y.toDouble() }.toFloat()
        calculateSliceAngles()
        invalidate()
    }

    private fun calculateSliceAngles() {
        sliceAngles.clear()
        var startAngle = 0f
        dataPoints.forEach { point ->
            val sweepAngle = (point.y / total) * 360f
            sliceAngles.add(startAngle + sweepAngle / 2)
            startAngle += sweepAngle
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (dataPoints.isEmpty()) return

        drawTitle(canvas)
        drawPieChart(canvas)
        drawLegend(canvas)
    }

    private fun drawTitle(canvas: Canvas) {
        paint.apply {
            color = Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(title, width / 2f, chartPadding - 10f, paint)
    }

    private fun drawPieChart(canvas: Canvas) {
        val radius = min(chartRect.width(), chartRect.height()) / 3
        centerPoint.set(chartRect.centerX(), chartRect.centerY())
        rectF.set(
            centerPoint.x - radius,
            centerPoint.y - radius,
            centerPoint.x + radius,
            centerPoint.y + radius
        )

        var startAngle = 0f
        dataPoints.forEachIndexed { index, point ->
            val sweepAngle = (point.y / total) * 360f
            paint.apply {
                style = Paint.Style.FILL
                color = getColorForIndex(index)
            }

            // Draw slice with offset if selected
            if (index == selectedSlice) {
                val midAngle = startAngle + sweepAngle / 2
                val offsetX = cos(Math.toRadians(midAngle.toDouble())) * highlightOffset
                val offsetY = sin(Math.toRadians(midAngle.toDouble())) * highlightOffset
                canvas.save()
                canvas.translate(offsetX.toFloat(), offsetY.toFloat())
            }

            canvas.drawArc(
                rectF,
                startAngle,
                sweepAngle - sliceSpacing,
                true,
                paint
            )

            if (index == selectedSlice) {
                canvas.restore()
            }

            startAngle += sweepAngle
        }
    }

    private fun drawLegend(canvas: Canvas) {
        val legendX = chartRect.right - 200f
        var legendY = chartRect.top + 50f
        val legendSpacing = 40f
        val colorBoxSize = 20f

        paint.textSize = 30f
        paint.textAlign = Paint.Align.LEFT

        dataPoints.forEachIndexed { index, point ->
            paint.color = getColorForIndex(index)
            paint.style = Paint.Style.FILL

            // Draw color box
            canvas.drawRect(
                legendX,
                legendY,
                legendX + colorBoxSize,
                legendY + colorBoxSize,
                paint
            )

            // Draw label
            paint.color = Color.BLACK
            canvas.drawText(
                "${point.label}: ${formatValue(point.y)} (${((point.y / total) * 100).toInt()}%)",
                legendX + colorBoxSize + 10f,
                legendY + colorBoxSize,
                paint
            )

            legendY += legendSpacing
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val dx = event.x - centerPoint.x
                val dy = event.y - centerPoint.y
                val touchAngle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
                val normalizedAngle = (touchAngle + 360) % 360

                var startAngle = 0f
                selectedSlice = -1
                dataPoints.forEachIndexed { index, point ->
                    val sweepAngle = (point.y / total) * 360f
                    if (normalizedAngle >= startAngle && normalizedAngle <= startAngle + sweepAngle) {
                        selectedSlice = index
                        invalidate()
                        return true
                    }
                    startAngle += sweepAngle
                }
                invalidate()
            }
        }
        return true
    }
} 