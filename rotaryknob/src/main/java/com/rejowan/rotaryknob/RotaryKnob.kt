package com.rejowan.rotaryknob


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
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


    enum class CircleStyle {
        SOLID, GRADIENT
    }

    enum class ProgressStyle {
        DOT, LINE
    }

    enum class IndicatorStyle {
        CIRCLE, LINE
    }

    enum class TextStyle {
        Normal, BOLD, ITALIC, BOLD_ITALIC
    }

    // paint
    private var circlePaint: Paint = Paint()
    private val borderPaint: Paint = Paint()
    private val progressPaint: Paint = Paint()
    private val progressFilled: Paint = Paint()
    private val indicatorPaint: Paint = Paint()
    private val labelPaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val subTextPaint: Paint = Paint()


    // attrs - circle
    var circleStyle = CircleStyle.GRADIENT
    var circleColor = Color.parseColor("#8062D6")
    var circleGradientCenterColor = Color.parseColor("#8062D6")
    var circleGradientOuterColor = Color.parseColor("#6040B8")

    // attrs - border
    var showBorder = true
    var borderColor = Color.parseColor("#8062D6")
    var borderWidth = 0f

    // progress normal
    var progressStyle = ProgressStyle.DOT
    var progressColor = Color.parseColor("#444444")
    var showBigProgress = true
    var bigProgressMultiplier = 0f
    var bigProgressDiff = 10

    // progress filled
    var progressFilledColor = Color.parseColor("#8062D6")
    var progressFilledMultiplier = 0f

    // indicator
    var indicatorStyle = IndicatorStyle.CIRCLE
    var indicatorColor = Color.parseColor("#FF0000")
    var indicatorSize = 10f

    // progress text
    var showProgressText = true
    var progressText = ""
    var progressTextColor = Color.parseColor("#FF0000")
    var progressTextSize = 10f
    var progressTextStyle = TextStyle.BOLD
    var progressTextFont: Typeface? = null

    // suffix text
    var showSuffixText = true
    var suffixText = ""
    var suffixTextColor = Color.parseColor("#FF0000")
    var suffixTextSize = 10f
    var suffixTextStyle = TextStyle.BOLD
    var suffixTextFont: Typeface? = null

    // Label
    var showLabel = true
    var labelText = ""
    var labelTextColor = Color.parseColor("#FF0000")
    var labelTextSize = 10f
    var labelTextStyle = TextStyle.BOLD
    var labelTextFont: Typeface? = null
    var labelMargin = 10f

    // enabled
    var knobEnable = true
    var touchToEnable = true
    var doubleTouchToEnable = true
    var disabledCircleColor = Color.parseColor("#FF0000")
    var disabledCircleGradientCenterColor = Color.parseColor("#FF0000")
    var disabledCircleGradientOuterColor = Color.parseColor("#FF0000")
    var disabledBorderColor = Color.parseColor("#FF0000")
    var disabledProgressColor = Color.parseColor("#FF0000")
    var disabledBigProgressColor = Color.parseColor("#FF0000")
    var disabledProgressFilledColor = Color.parseColor("#FF0000")
    var disabledIndicatorColor = Color.parseColor("#FF0000")
    var disabledProgressTextColor = Color.parseColor("#FF0000")
    var disabledsuffixTextColor = Color.parseColor("#FF0000")
    var disabledLabelTextColor = Color.parseColor("#FF0000")


    // value
    var min = 1
    var max = 30
    var currentProgress = 15


    // margin
    private var sideMargin = 0f
    private var radius = 0f
    private var mainCircleRadius = 0f
    private var outerCircleRadius = 0f
    private var progressRadius = 0f
    private var startingRadius = 0f

    private var midX = 0f
    private var midY = 0f

    private var startOffset = 45f
    private var endOffset = 45f
    private var sweepAngle = 360f - startOffset - endOffset

    private var normalDotSize = 0f
    private var largerDotSize = 0f

    init {

        initAttributes(attrs)

    }

    private fun initAttributes(attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotaryKnob)

        try {

            //  circle
            val circleStyleOrdinal =
                typedArray.getInt(R.styleable.RotaryKnob_circle_style, circleStyle.ordinal)
            circleStyle = CircleStyle.values()[circleStyleOrdinal]
            circleColor = typedArray.getColor(R.styleable.RotaryKnob_circle_color, circleColor)
            circleGradientCenterColor = typedArray.getColor(
                R.styleable.RotaryKnob_circle_gradient_center_color, circleGradientCenterColor
            )
            circleGradientOuterColor = typedArray.getColor(
                R.styleable.RotaryKnob_circle_gradient_outer_color, circleGradientOuterColor
            )

            // border
            showBorder = typedArray.getBoolean(R.styleable.RotaryKnob_show_border, showBorder)
            borderColor = typedArray.getColor(R.styleable.RotaryKnob_border_color, borderColor)
            borderWidth = convertDpToPixel(
                typedArray.getDimension(
                    R.styleable.RotaryKnob_border_width, borderWidth
                ), context
            )


            // progress normal
            val progressStyleOrdinal =
                typedArray.getInt(R.styleable.RotaryKnob_progress_style, progressStyle.ordinal)
            progressStyle = ProgressStyle.values()[progressStyleOrdinal]
            progressColor =
                typedArray.getColor(R.styleable.RotaryKnob_progress_color, progressColor)
            showBigProgress =
                typedArray.getBoolean(R.styleable.RotaryKnob_show_big_progress, showBigProgress)
            bigProgressMultiplier = typedArray.getFloat(
                R.styleable.RotaryKnob_big_progress_multiplier, bigProgressMultiplier
            )
            bigProgressDiff =
                typedArray.getInt(R.styleable.RotaryKnob_big_progress_diff, bigProgressDiff)


            // progress filled
            progressFilledColor = typedArray.getColor(
                R.styleable.RotaryKnob_progress_filled_color, progressFilledColor
            )
            progressFilledMultiplier = typedArray.getFloat(
                R.styleable.RotaryKnob_progress_filled_multiplier, progressFilledMultiplier
            )


            // indicator
            val indicatorStyleOrdinal =
                typedArray.getInt(R.styleable.RotaryKnob_indicator_style, indicatorStyle.ordinal)
            indicatorStyle = IndicatorStyle.values()[indicatorStyleOrdinal]
            indicatorColor = typedArray.getColor(
                R.styleable.RotaryKnob_indicator_color, indicatorColor
            )
            indicatorSize = convertDpToPixel(
                typedArray.getDimension(
                    R.styleable.RotaryKnob_indicator_size, indicatorSize
                ), context
            )


            // progress text
            showProgressText =
                typedArray.getBoolean(R.styleable.RotaryKnob_show_progress_text, showProgressText)
            progressText = typedArray.getString(R.styleable.RotaryKnob_progress_text).toString()
            progressTextColor = typedArray.getColor(
                R.styleable.RotaryKnob_progress_text_color, progressTextColor
            )
            progressTextSize = convertDpToPixel(
                typedArray.getDimension(
                    R.styleable.RotaryKnob_progress_text_size, progressTextSize
                ), context
            )
            val progressTextStyleOrdinal = typedArray.getInt(
                R.styleable.RotaryKnob_progress_text_style, progressTextStyle.ordinal
            )
            progressTextStyle = TextStyle.values()[progressTextStyleOrdinal]
            val progressFontString = typedArray.getString(R.styleable.RotaryKnob_progress_text_font)
            progressTextFont = if (progressFontString != null) {
                Typeface.createFromAsset(
                    context.assets, progressFontString
                )
            } else {
                null
            }


            // suffix text
            showSuffixText =
                typedArray.getBoolean(R.styleable.RotaryKnob_show_suffix_text, showSuffixText)
            suffixText = typedArray.getString(R.styleable.RotaryKnob_suffix_text).toString()
            suffixTextColor = typedArray.getColor(
                R.styleable.RotaryKnob_suffix_text_color, suffixTextColor
            )
            suffixTextSize = convertDpToPixel(
                typedArray.getDimension(
                    R.styleable.RotaryKnob_suffix_text_size, suffixTextSize
                ), context
            )
            val suffixTextStyleOrdinal =
                typedArray.getInt(R.styleable.RotaryKnob_suffix_text_style, suffixTextStyle.ordinal)
            suffixTextStyle = TextStyle.values()[suffixTextStyleOrdinal]
            val suffixFontString = typedArray.getString(R.styleable.RotaryKnob_suffix_text_font)
            suffixTextFont = if (suffixFontString != null) {
                Typeface.createFromAsset(
                    context.assets, suffixFontString
                )
            } else {
                null
            }


            // Label
            showLabel = typedArray.getBoolean(R.styleable.RotaryKnob_show_label, showLabel)
            labelText = typedArray.getString(R.styleable.RotaryKnob_label_text).toString()
            labelTextColor = typedArray.getColor(
                R.styleable.RotaryKnob_label_text_color, labelTextColor
            )
            labelTextSize = convertDpToPixel(
                typedArray.getDimension(
                    R.styleable.RotaryKnob_label_text_size, labelTextSize
                ), context
            )
            val labelTextStyleOrdinal =
                typedArray.getInt(R.styleable.RotaryKnob_label_text_style, labelTextStyle.ordinal)
            labelTextStyle = TextStyle.values()[labelTextStyleOrdinal]
            val labelTextFontString = typedArray.getString(R.styleable.RotaryKnob_label_text_font)
            labelTextFont = if (labelTextFontString != null) {
                Typeface.createFromAsset(
                    context.assets, labelTextFontString
                )
            } else {
                null
            }
            labelMargin = convertDpToPixel(
                typedArray.getDimension(
                    R.styleable.RotaryKnob_label_margin, labelMargin
                ), context
            )

            // enabled
            knobEnable = typedArray.getBoolean(R.styleable.RotaryKnob_knob_enable, knobEnable)
            touchToEnable =
                typedArray.getBoolean(R.styleable.RotaryKnob_touch_to_enable, touchToEnable)
            doubleTouchToEnable = typedArray.getBoolean(
                R.styleable.RotaryKnob_d_touch_to_enable, doubleTouchToEnable
            )
            disabledCircleColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_circle_color, disabledCircleColor
            )
            disabledCircleGradientCenterColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_circle_gradient_center_color,
                disabledCircleGradientCenterColor
            )
            disabledCircleGradientOuterColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_circle_gradient_outer_color,
                disabledCircleGradientOuterColor
            )
            disabledBorderColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_border_color, disabledBorderColor
            )
            disabledProgressColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_progress_color, disabledProgressColor
            )
            disabledBigProgressColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_big_progress_color, disabledBigProgressColor
            )
            disabledProgressFilledColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_progress_filled_color, disabledProgressFilledColor
            )
            disabledIndicatorColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_indicator_color, disabledIndicatorColor
            )
            disabledProgressTextColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_progress_text_color, disabledProgressTextColor
            )
            disabledsuffixTextColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_suffix_text_color, disabledsuffixTextColor
            )
            disabledLabelTextColor = typedArray.getColor(
                R.styleable.RotaryKnob_d_label_text_color, disabledLabelTextColor
            )


            // value
            min = typedArray.getInt(R.styleable.RotaryKnob_min, min)
            max = typedArray.getInt(R.styleable.RotaryKnob_max, max)
            currentProgress =
                typedArray.getInt(R.styleable.RotaryKnob_current_progress, currentProgress)


        } finally {
            typedArray.recycle()
        }





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
        textPaint.textSize = 100f
        textPaint.typeface = Typeface.DEFAULT_BOLD

        subTextPaint.isAntiAlias = true
        subTextPaint.color = Color.parseColor("#FFFFFF")
        subTextPaint.style = Paint.Style.FILL
        subTextPaint.textSize = 30f
        subTextPaint.typeface = Typeface.DEFAULT

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

        drawBorder(canvas)

        drawCircle(canvas)

        drawIndicator(canvas)

        drawLabel(canvas)

        drawText(canvas)

        //    drawCircle(canvas)


        canvas.restore()

    }

    private fun calculateAreas() {
        // mid x and y of the view
        midX = width / 2f
        midY = height / 2f

        // side margin of the view. total 5% of the min of midX and midY, and each side 2.5%
        sideMargin = (midX.coerceAtMost(midY) * (5f / 100))

        // radius of the view. 95% of the min of midX and midY
        radius = (midX.coerceAtMost(midY) * (90f / 100))


        if (showBorder) {

            // main circle radius. 70% of the radius
            mainCircleRadius = radius * (70f / 100)

            outerCircleRadius = if (borderWidth == 0f) {
                radius * (80f / 100)
            } else {
                mainCircleRadius + borderWidth
            }

            // progress circle radius.
            progressRadius = radius * (95f / 100)

        } else {

            // main circle radius. 70% of the radius
            mainCircleRadius = radius * (80f / 100)

            // outer circle radius. 80% of the radius
            outerCircleRadius = mainCircleRadius

            // progress circle radius.
            progressRadius = radius * (95f / 100)

        }


    }

    private fun setupCirclePaint() {
        circlePaint.isAntiAlias = true

        if (circleStyle == CircleStyle.SOLID) {
            circlePaint.color = circleColor
            circlePaint.style = Paint.Style.FILL
        } else {

            val startColor = Color.parseColor("#8062D6") // Light blue
            val endColor = Color.parseColor("#644AAC")   // Purple
            val radius = mainCircleRadius
            val gradient = RadialGradient(
                midX, midY, radius, startColor, endColor, Shader.TileMode.CLAMP
            )
            circlePaint.shader = gradient
        }

    }

    private fun drawText(canvas: Canvas) {

        canvas.save()

        val progressText = "$currentProgress"
        val progressTextWidth = textPaint.measureText(progressText)
        val progressTextHeight = textPaint.descent() - textPaint.ascent()
        val progressTextX = midX - progressTextWidth / 2
        val progressTextY = midY + progressTextHeight / 3f

        // Draw progress text
        canvas.drawText(progressText, progressTextX, progressTextY, textPaint)

        val additionalText = "%"
        val additionalTextWidth = subTextPaint.measureText(additionalText)
        val additionalTextHeight = subTextPaint.descent() - subTextPaint.ascent()
        val additionalTextX = progressTextX + progressTextWidth + additionalTextHeight / 4
        val additionalTextY = progressTextY - progressTextHeight + additionalTextHeight

        // Draw additional text on top-right corner
        canvas.drawText(additionalText, additionalTextX, additionalTextY, subTextPaint)


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

    private fun drawCircle(canvas: Canvas) {

        canvas.save()

        setupCirclePaint()

        canvas.drawCircle(midX, midY, mainCircleRadius, circlePaint)

        canvas.restore()


    }

    private fun drawBorder(canvas: Canvas) {

        canvas.save()

        setupBorderPaint()

        canvas.drawCircle(midX, midY, outerCircleRadius, borderPaint)

        canvas.restore()

    }

    private fun drawProgressSteps(canvas: Canvas) {

        canvas.save()

        setupProgressPaint()


        // large progress indices, it's every big_progress_diff-th index
        val largerDotIndices = mutableListOf<Int>()
        if (showBigProgress) {
            for (i in 0 until max) {
                if (i % bigProgressDiff == 0) {
                    largerDotIndices.add(i)
                }
            }
        }


        for (i in 0 until max) {
            // Calculate normalized progress for each dot
            val progress = i.toFloat() / (max - 1)

            // Calculate angle for current dot
            val angle = 360f - (endOffset + progress * sweepAngle)

            // Calculate x and y coordinates for the dot
            val x = midX + (progressRadius * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y = midY + (progressRadius * cos(Math.toRadians(angle.toDouble()))).toFloat()


            if (progressStyle == ProgressStyle.DOT) {

                var dotSize = 0f

                if (showBigProgress) {
                    if (i in largerDotIndices) {

                        dotSize = if (bigProgressMultiplier == 0f) {
                            progressRadius / 15 * (20f / max)
                        } else {
                            progressRadius / 30 * (20f / max) * bigProgressMultiplier
                        }

                        largerDotSize = dotSize

                    } else {
                        dotSize = progressRadius / 30 * (20f / max)
                        normalDotSize = dotSize
                    }


                } else {
                    dotSize = progressRadius / 30 * (20f / max)
                    normalDotSize = dotSize
                }

                // Draw the dot
                canvas.drawCircle(x, y, dotSize, progressPaint)
            } else {
                // Draw the line

                var lineSize = 0f

                if (i in largerDotIndices && max > 20) {
                    lineSize =
                        progressRadius / 10 * (20f / max) // Larger rectangle width for specified indices
                    progressPaint.strokeWidth = progressRadius / 20 * (20f / max)

                } else {
                    lineSize = progressRadius / 20 * (20f / max) // Regular rectangle width
                    progressPaint.strokeWidth = progressRadius / 40 * (20f / max)
                }


                val indicatorEndX =
                    midX + (progressRadius - lineSize) * sin(Math.toRadians(angle.toDouble())).toFloat()
                val indicatorEndY =
                    midY + (progressRadius - lineSize) * cos(Math.toRadians(angle.toDouble())).toFloat()

                canvas.drawLine(
                    x, y, indicatorEndX, indicatorEndY, progressPaint
                )


            }


        }

        canvas.restore()

    }

    private fun drawProgressStepsFilled(canvas: Canvas) {

        canvas.save()

        setupProgressFilledPaint()

        // large progress indices, it's every big_progress_diff-th index
        val largerDotIndices = mutableListOf<Int>()
        if (showBigProgress) {
            for (i in 0 until max) {
                if (i % bigProgressDiff == 0) {
                    largerDotIndices.add(i)
                }
            }
        }

        for (i in 0..<currentProgress) {
            // Calculate normalized progress for each dot
            val progress = i.toFloat() / (max - 1)

            // Calculate angle for current dot
            val angle = 360f - (endOffset + progress * sweepAngle)


            // Calculate x and y coordinates for the dot
            val x = midX + (progressRadius * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y = midY + (progressRadius * cos(Math.toRadians(angle.toDouble()))).toFloat()

            if (progressStyle == ProgressStyle.DOT) {

                val dotSize: Float = if (progressFilledMultiplier == 0f) {
                    if (i in largerDotIndices) {
                        largerDotSize * 1.5f
                    } else {
                        normalDotSize * 1.5f

                    }
                } else {
                    if (i in largerDotIndices) {
                        largerDotSize * progressFilledMultiplier
                    } else {
                        normalDotSize * progressFilledMultiplier
                    }
                }


                // Draw the dot
                canvas.drawCircle(x, y, dotSize, progressFilled)

            } else {

                var lineSize: Float

                if (i in largerDotIndices && max > 20) {

                    if (progressFilledMultiplier == 0f) {
                        lineSize = progressRadius / 10 * (25f / max)
                        progressFilled.strokeWidth = progressRadius / 20 * (25f / max)
                    } else {
                        lineSize = progressRadius / 10 * (25f / max)
                        progressFilled.strokeWidth = progressRadius / 20 * (25f / max) * progressFilledMultiplier
                    }


                } else {
                    if (progressFilledMultiplier == 0f) {
                        lineSize = progressRadius / 20 * (25f / max)
                        progressFilled.strokeWidth = progressRadius / 40 * (25f / max)
                    } else {
                        lineSize = progressRadius / 20 * (25f / max)
                        progressFilled.strokeWidth = progressRadius / 40 * (25f / max) * progressFilledMultiplier
                    }
                }

                val indicatorEndX =
                    midX + (progressRadius - lineSize) * sin(Math.toRadians(angle.toDouble())).toFloat()
                val indicatorEndY =
                    midY + (progressRadius - lineSize) * cos(Math.toRadians(angle.toDouble())).toFloat()

                canvas.drawLine(
                    x, y, indicatorEndX, indicatorEndY, progressFilled
                )

            }

        }

        canvas.restore()

    }

    private fun drawIndicator(canvas: Canvas) {


        canvas.save()

        val isCircle = true


        val progress1 = (currentProgress - 1).toFloat() / (max - 1)

        Log.e("drawIndicator", "progress1: $progress1")

        val angle = 360f - (endOffset + progress1 * sweepAngle)

        Log.e("drawIndicator", "angle: $angle")

        if (isCircle) {
            // Calculate x and y coordinates for the indicator
            val x =
                midX + (mainCircleRadius * 2 / 3 * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y =
                midY + (mainCircleRadius * 2 / 3 * cos(Math.toRadians(angle.toDouble()))).toFloat()

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

    private fun setupBorderPaint() {
        borderPaint.isAntiAlias = true
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.FILL
    }

    private fun setupProgressPaint() {

        progressPaint.isAntiAlias = true
        progressPaint.color = progressColor
        progressPaint.style = Paint.Style.FILL

    }

    private fun setupProgressFilledPaint() {

        progressFilled.isAntiAlias = true
        progressFilled.color = progressFilledColor
        progressFilled.style = Paint.Style.FILL
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
                    currentProgress = temp
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
                    currentProgress = temp
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