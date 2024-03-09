package com.rejowan.rotaryknobsample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class RotaryKnob @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // paint
    private var circlePaint: Paint = Paint()
    private val outerCirclePaint: Paint = Paint()
    private val stepCirclePaint: Paint = Paint()
    private val stepCircleFillPaint: Paint = Paint()
    private val indicatorPaint: Paint = Paint()

    // bound
    private val circleBounds = RectF()

    // margin
    private var sideMargin = 0f
    private var innerMargin = 0f

    // radius
    private var radius = 0f
    private var mainCircleRadius = 0f
    private var outerCircleRadius = 0f
    private var progressRadius = 0f

    private var midX = 0f
    private var midY = 0f


    init {
        // Do something here


        initAttributes(attrs)

    }

    private fun initAttributes(attrs: AttributeSet?) {

        circlePaint.isAntiAlias = true
        circlePaint.color = Color.parseColor("#FF0000")
        circlePaint.style = Paint.Style.FILL

        outerCirclePaint.isAntiAlias = true
        outerCirclePaint.color = Color.parseColor("#0000FF")
        outerCirclePaint.style = Paint.Style.FILL

        stepCirclePaint.isAntiAlias = true
        stepCirclePaint.color = Color.parseColor("#000000")
        stepCirclePaint.style = Paint.Style.FILL

        stepCircleFillPaint.isAntiAlias = true
        stepCircleFillPaint.color = Color.parseColor("#ff00ff")
        stepCircleFillPaint.style = Paint.Style.FILL

        indicatorPaint.isAntiAlias = true
        indicatorPaint.color = Color.parseColor("#00FF00")
        indicatorPaint.style = Paint.Style.FILL
        indicatorPaint.strokeWidth = 7f

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minWidth = Utils.convertDpToPixel(160f, context).toInt()
        val minHeight = Utils.convertDpToPixel(160f, context).toInt()

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> minWidth.coerceAtMost(widthSize)
            else -> minWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> minHeight.coerceAtMost(heightSize)
            else -> minHeight
        }

        setMeasuredDimension(width, height)

    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()

        calculateAreas()

        drawProgressCircle(canvas)

        drawProgressFillCircle(canvas)

        drawOuterCircle(canvas)

        drawMainCircle(canvas)

        drawIndicator(canvas)

        //    drawCircle(canvas)


        canvas.restore()

    }
    private fun calculateAreas() {
        // mid x and y of the view
        midX = width / 2f
        midY = height / 2f

        // side margin of the view. total 5% of the min of midX and midY, and each side 2.5%
        sideMargin = (midX.coerceAtMost(midY) * (5f / 100))

        // inner margin of the view. total 7.5% of the min of midX and midY, and each side 3.75%
        innerMargin = (midX.coerceAtMost(midY) * (7.5f / 100))

        // radius of the view. 95% of the min of midX and midY
        radius = (midX.coerceAtMost(midY) * (95f / 100))

        // main circle radius. 70% of the radius
        mainCircleRadius = radius * (70f / 100)

        // outer circle radius. 80% of the radius
        outerCircleRadius = radius * (80f / 100)

        // progress circle radius. 90% of the radius
        progressRadius = radius * (90f / 100)


    }

    private fun drawMainCircle(canvas: Canvas) {

        canvas.save()

        canvas.drawCircle(midX, midY, mainCircleRadius, circlePaint)

        canvas.restore()


    }

    private fun drawOuterCircle(canvas: Canvas) {

        canvas.save()

        canvas.drawCircle(midX, midY, outerCircleRadius, outerCirclePaint)

        canvas.restore()

    }

    private fun drawProgressCircle(canvas: Canvas) {

        canvas.save()


        val max = 30
        val startOffset = 30f
        val endOffset = 30f

        val sweepAngle = 360f - startOffset - endOffset

        val largerDotIndices = listOf(0, (max - 1) / 4, (max - 1) / 2, 3 * (max - 1) / 4, max - 1)


        for (i in 0 until max) {
            // Calculate normalized progress for each dot
            val progress = i.toFloat() / (max - 1)

            // Calculate angle for current dot
            val angle = 360f - (endOffset + progress * sweepAngle)

            // Calculate x and y coordinates for the dot
            val x = midX + (progressRadius * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y = midY + (progressRadius * cos(Math.toRadians(angle.toDouble()))).toFloat()

            // Calculate dot size based on max count and available space
            val dotSize = if (i in largerDotIndices  && max > 20) {
                progressRadius / 15 * (15f / max) // Larger circle size for specified indices
            } else {
                progressRadius / 25 * (15f / max) // Regular circle size
            }

            // Draw the dot
            canvas.drawCircle(x, y, dotSize, stepCirclePaint)
        }

        canvas.restore()

    }

    private fun drawProgressFillCircle(canvas: Canvas) {

        canvas.save()


        val max = 30
        val startOffset = 30f
        val endOffset = 30f

        val sweepAngle = 360f - startOffset - endOffset

        val largerDotIndices = listOf(0, (max - 1) / 4, (max - 1) / 2, 3 * (max - 1) / 4, max - 1)


        for (i in 0..2) {
            // Calculate normalized progress for each dot
            val progress = i.toFloat() / (max - 1)

            // Calculate angle for current dot
            val angle = 360f - (endOffset + progress * sweepAngle)

            // Calculate x and y coordinates for the dot
            val x = midX + (progressRadius * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y = midY + (progressRadius * cos(Math.toRadians(angle.toDouble()))).toFloat()

            // Calculate dot size based on max count and available space
            val dotSize = if (i in largerDotIndices && max > 20) {
                progressRadius / 15 * (15f / max) // Larger circle size for specified indices
            } else {
                progressRadius / 25 * (15f / max) // Regular circle size
            }

            // Draw the dot
            canvas.drawCircle(x, y, dotSize, stepCircleFillPaint)
        }

        canvas.restore()

    }

    private fun drawIndicator(canvas: Canvas) {

        canvas.save()

        val max = 30
        val startOffset = 30f
        val endOffset = 30f
        val sweepAngle = 360f - startOffset - endOffset

        val progress = 2f / (max - 1) // Assuming 1 dots are filled, change this based on your actual progress

        val angle = 360f - (endOffset + progress * sweepAngle)


        val startMargin = radius * (2f / 5) // Starting from 2/5 radius margin
        val indicatorStartX = midX + (startMargin * sin(Math.toRadians(angle.toDouble()))).toFloat()
        val indicatorStartY = midY + (startMargin * cos(Math.toRadians(angle.toDouble()))).toFloat()

        // Calculate the length of the indicator line (1/3 of the radius)
        val indicatorLength = radius * (1f / 5)

        // Calculate the ending position of the indicator line
        val indicatorEndX = midX + (startMargin + indicatorLength) * sin(Math.toRadians(angle.toDouble())).toFloat()
        val indicatorEndY = midY + (startMargin + indicatorLength) * cos(Math.toRadians(angle.toDouble())).toFloat()

        // Draw the indicator line
        canvas.drawLine(indicatorStartX, indicatorStartY, indicatorEndX, indicatorEndY, indicatorPaint)


        canvas.restore()

    }


}