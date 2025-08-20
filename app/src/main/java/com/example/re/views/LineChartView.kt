package com.example.re.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import kotlin.math.max
import kotlin.math.min

class LineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseChartView(context, attrs, defStyleAttr) {

    private var dataPoints = listOf<DataPoint>()
    private var maxY = 0f
    private var minX = 0f
    private var maxX = 0f
    private var title = ""
    private var scaleX = 1f
    private var translateX = 0f

    private val path = Path()
    private val scaleDetector = ScaleGestureDetector(context, ScaleListener())

    fun setData(points: List<DataPoint>, chartTitle: String) {
        dataPoints = points
        title = chartTitle
        maxY = points.maxOf { it.y }
        minX = points.minOf { it.x }
        maxX = points.maxOf { it.x }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (dataPoints.isEmpty()) return

        drawTitle(canvas)
        drawAxes(canvas)
        drawData(canvas)
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

        // Draw X-axis labels
        paint.textAlign = Paint.Align.CENTER
        dataPoints.forEach { point ->
            val xPos = mapXToCanvas(point.x)
            canvas.drawText(point.x.toInt().toString(), xPos, chartRect.bottom + 30f, paint)
        }
    }

    private fun drawData(canvas: Canvas) {
        if (dataPoints.size < 2) return

        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 4f
            color = getColorForIndex(0)
        }

        path.reset()
        path.moveTo(mapXToCanvas(dataPoints[0].x), mapYToCanvas(dataPoints[0].y))
        
        for (i in 1 until dataPoints.size) {
            path.lineTo(mapXToCanvas(dataPoints[i].x), mapYToCanvas(dataPoints[i].y))
        }

        canvas.drawPath(path, paint)

        // Draw points
        paint.style = Paint.Style.FILL
        dataPoints.forEach { point ->
            canvas.drawCircle(mapXToCanvas(point.x), mapYToCanvas(point.y), 8f, paint)
        }
    }

    private fun mapYToCanvas(y: Float): Float {
        return chartRect.bottom - (y / maxY) * (chartRect.height())
    }

    private fun mapXToCanvas(x: Float): Float {
        val range = maxX - minX
        val scaledX = (x - minX) / range
        return chartRect.left + (scaledX * chartRect.width() * scaleX) + translateX
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                if (!scaleDetector.isInProgress) {
                    translateX += event.x - lastTouchX
                    translateX = max(min(0f, translateX), -chartRect.width() * (scaleX - 1))
                    lastTouchX = event.x
                    invalidate()
                }
            }
            MotionEvent.ACTION_DOWN -> lastTouchX = event.x
        }
        return true
    }

    private var lastTouchX = 0f

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleX *= detector.scaleFactor
            scaleX = scaleX.coerceIn(1f, 3f)
            invalidate()
            return true
        }
    }
} 