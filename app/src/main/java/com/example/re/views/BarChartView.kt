package com.example.re.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import kotlin.math.max
import kotlin.math.min

class BarChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseChartView(context, attrs, defStyleAttr) {

    private var dataPoints = listOf<DataPoint>()
    private var maxY = 0f
    private var title = ""
    private var scaleX = 1f
    private var translateX = 0f
    private var selectedBar = -1

    private val scaleDetector = ScaleGestureDetector(context, ScaleListener())
    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        color = Color.RED
    }

    fun setData(points: List<DataPoint>, chartTitle: String) {
        dataPoints = points
        title = chartTitle
        maxY = points.maxOf { it.y }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (dataPoints.isEmpty()) return

        drawTitle(canvas)
        drawAxes(canvas)
        drawBars(canvas)
    }

    private fun drawTitle(canvas: Canvas) {
        paint.apply {
            color = Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(title, width / 2f, chartPadding - 10f, paint)
    }

    private fun drawAxes(canvas: Canvas) {
        paint.apply {
            color = Color.GRAY
            strokeWidth = 2f
            style = Paint.Style.STROKE
        }

        // Draw axes
        canvas.drawLine(chartRect.left, chartRect.bottom, chartRect.right, chartRect.bottom, paint)
        canvas.drawLine(chartRect.left, chartRect.top, chartRect.left, chartRect.bottom, paint)

        // Draw Y-axis labels
        paint.textSize = 30f
        paint.textAlign = Paint.Align.RIGHT
        val ySteps = calculateYAxisSteps(maxY)
        ySteps.forEach { y ->
            val yPos = mapYToCanvas(y)
            canvas.drawText(formatValue(y), chartRect.left - 10f, yPos, paint)
            canvas.drawLine(chartRect.left - 5f, yPos, chartRect.left, yPos, paint)
        }
    }

    private fun drawBars(canvas: Canvas) {
        if (dataPoints.isEmpty()) return

        val barWidth = calculateBarWidth()
        val spacing = barWidth * 0.2f

        dataPoints.forEachIndexed { index, point ->
            val left = mapXToCanvas(index.toFloat()) - barWidth / 2 + spacing / 2
            val right = left + barWidth - spacing
            val top = mapYToCanvas(point.y)
            val bottom = chartRect.bottom

            barPaint.apply {
                style = Paint.Style.FILL
                color = getColorForIndex(index)
            }

            canvas.drawRect(left, top, right, bottom, barPaint)

            if (index == selectedBar) {
                canvas.drawRect(left, top, right, bottom, highlightPaint)
            }

            // Draw X-axis label
            paint.apply {
                color = Color.BLACK
                textSize = 30f
                textAlign = Paint.Align.CENTER
            }
            canvas.drawText(
                point.label ?: point.x.toInt().toString(),
                (left + right) / 2,
                chartRect.bottom + 30f,
                paint
            )

            // Draw value above bar
            if (index == selectedBar) {
                canvas.drawText(
                    formatValue(point.y),
                    (left + right) / 2,
                    top - 10f,
                    paint
                )
            }
        }
    }

    private fun calculateBarWidth(): Float {
        val availableWidth = chartRect.width() * scaleX
        return availableWidth / (dataPoints.size + 1)
    }

    private fun mapYToCanvas(y: Float): Float {
        return chartRect.bottom - (y / maxY) * chartRect.height()
    }

    private fun mapXToCanvas(x: Float): Float {
        val barWidth = calculateBarWidth()
        return chartRect.left + (x + 1) * barWidth + translateX
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                updateSelectedBar(event.x)
            }
            MotionEvent.ACTION_MOVE -> {
                if (!scaleDetector.isInProgress) {
                    translateX += event.x - lastTouchX
                    translateX = max(min(0f, translateX), -chartRect.width() * (scaleX - 1))
                    lastTouchX = event.x
                    updateSelectedBar(event.x)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                updateSelectedBar(event.x)
            }
        }
        return true
    }

    private var lastTouchX = 0f

    private fun updateSelectedBar(touchX: Float) {
        val barWidth = calculateBarWidth()
        dataPoints.forEachIndexed { index, _ ->
            val barCenter = mapXToCanvas(index.toFloat())
            if (touchX >= barCenter - barWidth / 2 && touchX <= barCenter + barWidth / 2) {
                if (selectedBar != index) {
                    selectedBar = index
                    invalidate()
                }
                return
            }
        }
        if (selectedBar != -1) {
            selectedBar = -1
            invalidate()
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleX *= detector.scaleFactor
            scaleX = scaleX.coerceIn(1f, 3f)
            invalidate()
            return true
        }
    }
} 