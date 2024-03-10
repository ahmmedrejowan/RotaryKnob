package com.rejowan.rotaryknob


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class RotaryKnob @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // paint
    private var circlePaint: Paint = Paint()
    private val outerCirclePaint: Paint = Paint()
    private val stepPaint: Paint = Paint()
    private val stepFilledPaint: Paint = Paint()
    private val indicatorPaint: Paint = Paint()
    private val labelPaint: Paint = Paint()
    private val textPaint: Paint = Paint()


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

    private var startingRadius = 0f


    init {
        // Do something here


        initAttributes(attrs)

    }

    private fun initAttributes(attrs: AttributeSet?) {

        circlePaint.isAntiAlias = true
//        circlePaint.color = Color.parseColor("#FF0000")
//        circlePaint.style = Paint.Style.FILL


        outerCirclePaint.isAntiAlias = true
        outerCirclePaint.color = Color.parseColor("#8062D6")
        outerCirclePaint.style = Paint.Style.FILL

        stepPaint.isAntiAlias = true
        stepPaint.color = Color.parseColor("#000000")
        stepPaint.style = Paint.Style.FILL

        stepFilledPaint.isAntiAlias = true
        stepFilledPaint.color = Color.parseColor("#8062D6")
        stepFilledPaint.style = Paint.Style.FILL

        indicatorPaint.isAntiAlias = true
        indicatorPaint.color = Color.parseColor("#FFFFFF")
        indicatorPaint.style = Paint.Style.FILL
        indicatorPaint.strokeWidth = 7f

        labelPaint.isAntiAlias = true
        labelPaint.color = Color.parseColor("#000000")
        labelPaint.style = Paint.Style.FILL
        labelPaint.textSize = 40f

        textPaint.isAntiAlias = true
        textPaint.color = Color.parseColor("#FFFFFF")
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 70f
        textPaint.typeface = Typeface.DEFAULT_BOLD

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minWidth = convertDpToPixel(160f, context).toInt()
        val minHeight = convertDpToPixel(160f, context).toInt()

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

        drawProgressSteps(canvas)

        drawProgressStepsFilled(canvas)

        drawOuterCircle(canvas)

        drawMainCircle(canvas)

        drawIndicator(canvas)

        drawLabel(canvas)

        drawText(canvas)

        //    drawCircle(canvas)


        canvas.restore()

    }

    private fun drawText(canvas: Canvas) {

        canvas.save()

        val text = "$progress"
        val textWidth = textPaint.measureText(text)
        val textHeight = textPaint.descent() - textPaint.ascent()
        val textX = midX - textWidth / 2
        val textY = midY + textHeight / 2

        canvas.drawText(text, textX, textY, textPaint)

        canvas.restore()

    }

    private fun drawLabel(canvas: Canvas) {

        canvas.save()

        val text = "Progress"
        val textWidth = labelPaint.measureText(text)
        val textHeight = labelPaint.descent() - labelPaint.ascent()
        val textX = midX - textWidth / 2
        val textY = midY + radius

        canvas.drawText(text, textX, textY, labelPaint)

        canvas.restore()

    }


    private var midX = 0f
    private var midY = 0f

    var max = 20
    var startOffset = 45f
    var endOffset = 45f
    var sweepAngle = 360f - startOffset - endOffset
    var progress = 1


    private fun calculateAreas() {
        // mid x and y of the view
        midX = width / 2f
        midY = height / 2f

        // side margin of the view. total 5% of the min of midX and midY, and each side 2.5%
        sideMargin = (midX.coerceAtMost(midY) * (5f / 100))

        // radius of the view. 95% of the min of midX and midY
        radius = (midX.coerceAtMost(midY) * (90f / 100))

        // main circle radius. 70% of the radius
        mainCircleRadius = radius * (70f / 100)

        // outer circle radius. 80% of the radius
        outerCircleRadius = radius * (80f / 100)

        // progress circle radius.
        progressRadius = radius * (95f / 100)


    }

    private fun drawMainCircle(canvas: Canvas) {

        canvas.save()

        val startColor = Color.parseColor("#8062D6") // Light blue
        val endColor = Color.parseColor("#644AAC")   // Purple

        // Calculate the radius of the circle
        val radius = mainCircleRadius

        // Create a RadialGradient object
        val gradient = RadialGradient(
            midX, midY, radius, startColor, endColor, Shader.TileMode.CLAMP
        )

        // Set the shader to the paint object
        circlePaint.shader = gradient


        canvas.drawCircle(midX, midY, mainCircleRadius, circlePaint)

        canvas.restore()


    }

    private fun drawOuterCircle(canvas: Canvas) {

        canvas.save()

        canvas.drawCircle(midX, midY, outerCircleRadius, outerCirclePaint)

        canvas.restore()

    }

    private fun drawProgressSteps(canvas: Canvas) {

        val isCircle = false

        canvas.save()

        val largerDotIndices = listOf(0, (max - 1) / 4, (max - 1) / 2, 3 * (max - 1) / 4, max - 1)


        for (i in 0 until max) {
            // Calculate normalized progress for each dot
            val progress = i.toFloat() / (max - 1)

            // Calculate angle for current dot
            val angle = 360f - (endOffset + progress * sweepAngle)

            // Calculate x and y coordinates for the dot
            val x = midX + (progressRadius * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y = midY + (progressRadius * cos(Math.toRadians(angle.toDouble()))).toFloat()


            if (isCircle) {
                // Calculate dot size based on max count and available space
                val dotSize = if (i in largerDotIndices && max > 20) {
                    progressRadius / 15 * (20f / max) // Larger circle size for specified indices
                } else {
                    progressRadius / 30 * (20f / max) // Regular circle size
                }

                // Draw the dot
                canvas.drawCircle(x, y, dotSize, stepPaint)
            } else {
                // Draw the line

                var lineSize = 0f

                if (i in largerDotIndices && max > 20) {
                    lineSize =
                        progressRadius / 10 * (20f / max) // Larger rectangle width for specified indices
                    stepPaint.strokeWidth = progressRadius / 20 * (20f / max)

                } else {
                    lineSize = progressRadius / 20 * (20f / max) // Regular rectangle width
                    stepPaint.strokeWidth = progressRadius / 40 * (20f / max)
                }


                val indicatorEndX =
                    midX + (progressRadius - lineSize) * sin(Math.toRadians(angle.toDouble())).toFloat()
                val indicatorEndY =
                    midY + (progressRadius - lineSize) * cos(Math.toRadians(angle.toDouble())).toFloat()

                canvas.drawLine(
                    x, y, indicatorEndX, indicatorEndY, stepPaint
                )


            }


        }

        canvas.restore()

    }


    private fun drawIndicator(canvas: Canvas) {


        canvas.save()

        val isCircle = true


        val progress1 = (progress - 1).toFloat() / (max - 1)

        Log.e("drawIndicator", "progress1: $progress1")

        val angle = 360f - (endOffset + progress1 * sweepAngle)

        Log.e("drawIndicator", "angle: $angle")

        if (isCircle){
            // Calculate x and y coordinates for the indicator
            val x = midX + (mainCircleRadius * 2/3 * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y = midY + (mainCircleRadius * 2/3 * cos(Math.toRadians(angle.toDouble()))).toFloat()

            // Calculate the size of the indicator
            val indicatorSize = mainCircleRadius / 8

            // Draw the indicator
            canvas.drawCircle(x, y, indicatorSize, indicatorPaint)

        } else {
            // Calculate the starting position of the indicator line

            val startMargin = radius * (2f / 5) // Starting from 2/5 radius margin
            val indicatorStartX =
                midX + (startMargin * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val indicatorStartY =
                midY + (startMargin * cos(Math.toRadians(angle.toDouble()))).toFloat()

            // Calculate the length of the indicator line (1/3 of the radius)
            val indicatorLength = radius * (1f / 5)

            // Calculate the ending position of the indicator line
            val indicatorEndX =
                midX + (startMargin + indicatorLength) * sin(Math.toRadians(angle.toDouble())).toFloat()
            val indicatorEndY =
                midY + (startMargin + indicatorLength) * cos(Math.toRadians(angle.toDouble())).toFloat()

            // Draw the indicator line
            canvas.drawLine(
                indicatorStartX, indicatorStartY, indicatorEndX, indicatorEndY, indicatorPaint
            )

        }

        canvas.restore()

    }


    private fun drawProgressStepsFilled(canvas: Canvas) {

        canvas.save()

        val largerDotIndices = listOf(0, (max - 1) / 4, (max - 1) / 2, 3 * (max - 1) / 4, max - 1)

        for (i in 0..<progress) {
            // Calculate normalized progress for each dot
            val progress = i.toFloat() / (max - 1)

            Log.e("drawProgressFillCircle", "progress: $progress")

            // Calculate angle for current dot
            val angle = 360f - (endOffset + progress * sweepAngle)

            Log.e("drawProgressFillCircle", "angle: $angle")

            // Calculate x and y coordinates for the dot
            val x = midX + (progressRadius * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y = midY + (progressRadius * cos(Math.toRadians(angle.toDouble()))).toFloat()

            // Calculate dot size based on max count and available space
            val dotSize = if (i in largerDotIndices && max > 20) {
                progressRadius / 15 * (25f / max) // Larger circle size for specified indices
            } else {
                progressRadius / 30 * (25f / max) // Regular circle size
            }

            // Draw the dot
            canvas.drawCircle(x, y, dotSize, stepFilledPaint)


        }

        canvas.restore()

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        val action = event.actionMasked
        val pointerIndex = event.actionIndex

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {

            val touchX = event.getX(pointerIndex)
            val touchY = event.getY(pointerIndex)


            val distanceToCenter = sqrt((midX - touchX).pow(2) + (midY - touchY).pow(2))


            if (distanceToCenter in (mainCircleRadius - 5)..radius) {

                parent.requestDisallowInterceptTouchEvent(true)


                val dx = touchX - midX
                val dy = touchY - midY

                var currentAngle = (atan2(dy.toDouble(), dx.toDouble()) * 180 / Math.PI).toFloat()
                Log.e("onTouchEvent", "currentAngle: $currentAngle")

                currentAngle -= 90
                Log.e("onTouchEvent", "currentAngle - 90: $currentAngle")

                currentAngle -= startOffset

                if (currentAngle < 0) {
                    currentAngle += 360
                    Log.e("onTouchEvent", "currentAngle + 360: $currentAngle")
                }

                var reVerseAngle = 360 - currentAngle
                Log.e("onTouchEvent", "reVerseAngle: $reVerseAngle")

                val temp1 = (reVerseAngle - 360) / sweepAngle
                Log.e("onTouchEvent", "temp1: $temp1")

                val temp = -(temp1 * (max - 1)).toInt() + 1
                Log.e("onTouchEvent", "temp: $temp")

                return if (temp in 1..max) {
                    progress = temp
                    invalidate()
                    true
                } else {
                    false
                }


            }

            return true

        } else if (action == MotionEvent.ACTION_MOVE) {

            val touchX = event.getX(pointerIndex)
            val touchY = event.getY(pointerIndex)

            val distanceToCenter = sqrt((midX - touchX).pow(2) + (midY - touchY).pow(2))

            Log.e("onTouchEvent", "distanceToCenter: $distanceToCenter")

            startingRadius = if (startingRadius == 0f) distanceToCenter else startingRadius

            Log.e("onTouchEvent", "startingRadius: $startingRadius")

            if (startingRadius < radius) {

                val dx = touchX - midX
                val dy = touchY - midY

                var currentAngle = (atan2(dy.toDouble(), dx.toDouble()) * 180 / Math.PI).toFloat()
                Log.e("onTouchEvent", "currentAngle: $currentAngle")

                currentAngle -= 90
                Log.e("onTouchEvent", "currentAngle - 90: $currentAngle")

                currentAngle -= startOffset

                if (currentAngle < 0) {
                    currentAngle += 360
                    Log.e("onTouchEvent", "currentAngle + 360: $currentAngle")
                }

                var reVerseAngle = 360 - currentAngle
                Log.e("onTouchEvent", "reVerseAngle: $reVerseAngle")

                val temp1 = (reVerseAngle - 360) / sweepAngle
                Log.e("onTouchEvent", "temp1: $temp1")

                val temp = -(temp1 * (max - 1)).toInt() + 1
                Log.e("onTouchEvent", "temp: $temp")

                return if (temp in 1..max) {
                    progress = temp
                    invalidate()
                    true
                } else {
                    false
                }

            }

        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {

            startingRadius = 0f

            return true

        }


        return super.onTouchEvent(event)

    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }


}