package com.rejowan.rotaryknob


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

class RotaryKnob @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    enum class CircleStyle {
        SOLID, GRADIENT
    }

    enum class SizeStyle {
        CIRCLE, LINE
    }

    enum class TextStyle {
        NORMAL, BOLD, ITALIC, BOLD_ITALIC
    }

    // paint
    private var circlePaint: Paint = Paint()
    private val borderPaint: Paint = Paint()
    private val progressPaint: Paint = Paint()
    private val progressFilled: Paint = Paint()
    private val indicatorPaint: Paint = Paint()
    private val labelPaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val suffixTextPaint: Paint = Paint()
    private val knobImagePaint: Paint = Paint()


    // attrs - circle
    var circleStyle = CircleStyle.GRADIENT
        set(value) {
            field = value
            invalidate()
        }
    var circleColor = Color.parseColor("#8062D6")
        set(value) {
            field = value
            invalidate()
        }
    var circleGradientCenterColor = Color.parseColor("#8062D6")
        set(value) {
            field = value
            invalidate()
        }
    var circleGradientOuterColor = Color.parseColor("#644AAC")
        set(value) {
            field = value
            invalidate()
        }

    // attrs - border
    var showBorder = true
        set(value) {
            field = value
            invalidate()
        }
    var borderColor = Color.parseColor("#8A73CD")
        set(value) {
            field = value
            invalidate()
        }
    var borderWidth = 0f
        set(value) {
            field = value
            invalidate()
        }

    // progress normal
    var progressStyle = SizeStyle.CIRCLE
        set(value) {
            field = value
            invalidate()
        }
    var progressColor = Color.parseColor("#444444")
        set(value) {
            field = value
            invalidate()
        }
    var showBigProgress = true
        set(value) {
            field = value
            invalidate()
        }
    var bigProgressMultiplier = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bigProgressDiff = 10
        set(value) {
            field = value
            invalidate()
        }

    // progress filled
    var progressFilledColor = Color.parseColor("#8062D6")
        set(value) {
            field = value
            invalidate()
        }
    var progressFilledMultiplier = 0f
        set(value) {
            field = value
            invalidate()
        }

    // indicator
    var indicatorStyle = SizeStyle.CIRCLE
        set(value) {
            field = value
            invalidate()
        }
    var indicatorColor = Color.parseColor("#FFFFFF")
        set(value) {
            field = value
            invalidate()
        }
    var indicatorSize = 0f
        set(value) {
            field = value
            invalidate()
        }


    // progress text
    var showProgressText = true
        set(value) {
            field = value
            invalidate()
        }
    var progressText = ""
        set(value) {
            field = value
            invalidate()
        }
    var progressTextColor = Color.parseColor("#FFFFFF")
        set(value) {
            field = value
            invalidate()
        }
    var progressTextSize = 100f
        set(value) {
            field = value
            invalidate()
        }
    var progressTextStyle = TextStyle.BOLD
        set(value) {
            field = value
            invalidate()
        }
    var progressTextFont: Typeface? = null
        set(value) {
            field = value
            invalidate()
        }

    // suffix text
    var showSuffixText = false
        set(value) {
            field = value
            invalidate()
        }
    var suffixText = ""
        set(value) {
            field = value
            invalidate()
        }
    var suffixTextColor = Color.parseColor("#FFFFFF")
        set(value) {
            field = value
            invalidate()
        }
    var suffixTextSize = 30f
        set(value) {
            field = value
            invalidate()
        }
    var suffixTextStyle = TextStyle.NORMAL
        set(value) {
            field = value
            invalidate()
        }
    var suffixTextFont: Typeface? = null
        set(value) {
            field = value
            invalidate()
        }

    // Label
    var showLabel = true
        set(value) {
            field = value
            invalidate()
        }
    var labelText = "Label"
        set(value) {
            field = value
            invalidate()
        }
    var labelTextColor = Color.parseColor("#444444")
        set(value) {
            field = value
            invalidate()
        }
    var labelTextSize = 40f
        set(value) {
            field = value
            invalidate()
        }
    var labelTextStyle = TextStyle.NORMAL
        set(value) {
            field = value
            invalidate()
        }
    var labelTextFont: Typeface? = null
        set(value) {
            field = value
            invalidate()
        }
    var labelMargin = 0f
        set(value) {
            field = value
            invalidate()
        }

    // enabled
    var knobEnable = true
        set(value) {
            field = value
            invalidate()
        }
    var touchToEnable = true
        set(value) {
            field = value
            invalidate()
        }
    var doubleTouchToEnable = true
        set(value) {
            field = value
            invalidate()
        }
    var disabledCircleColor = Color.parseColor("#555555")
        set(value) {
            field = value
            invalidate()
        }
    var disabledCircleGradientCenterColor = Color.parseColor("#555555")
        set(value) {
            field = value
            invalidate()
        }
    var disabledCircleGradientOuterColor = Color.parseColor("#444444")
        set(value) {
            field = value
            invalidate()
        }
    var disabledBorderColor = Color.parseColor("#333333")
        set(value) {
            field = value
            invalidate()
        }
    var disabledProgressColor = Color.parseColor("#888888")
        set(value) {
            field = value
            invalidate()
        }
    var disabledBigProgressColor = Color.parseColor("#888888")
        set(value) {
            field = value
            invalidate()
        }
    var disabledProgressFilledColor = Color.parseColor("#555555")
        set(value) {
            field = value
            invalidate()
        }
    var disabledIndicatorColor = Color.parseColor("#cccccc")
        set(value) {
            field = value
            invalidate()
        }
    var disabledProgressTextColor = Color.parseColor("#cccccc")
        set(value) {
            field = value
            invalidate()
        }
    var disabledsuffixTextColor = Color.parseColor("#cccccc")
        set(value) {
            field = value
            invalidate()
        }
    var disabledLabelTextColor = Color.parseColor("#555555")
        set(value) {
            field = value
            invalidate()
        }

    // knob_image
    var knobImageID = 0
        set(value) {
            field = value
            invalidate()
        }
    lateinit var knobImage: Bitmap
    var isKnobRotate = false
        get() = field
        set(value) {
            field = value
        }

    // value
    var min = 0
        set(value) {
            field = value
            invalidate()
        }
    var max = 30
        set(value) {
            field = value
            invalidate()
        }
    var currentProgress = 0
        set(value) {
            field = value
            invalidate()
        }

    var progressChangeListener: OnProgressChangeListener? = null
    var knobEnableListener: OnKnobEnableListener? = null

    // margin
    private var sideMargin = 0f
    private var radius = 0f
    private var mainCircleRadius = 0f
    private var borderRadius = 0f
    private var progressRadius = 0f
    private var startingRadius = 0f

    private var midX = 0f
    private var midY = 0f

    private var startOffset = 45f
    private var endOffset = 45f
    private var sweepAngle = 360f - startOffset - endOffset

    private var normalDotSize = 0f
    private var largerDotSize = 0f

    private var gestureDetector: GestureDetector? = null

    init {

        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    val pointerIndex = e.actionIndex
                    val touchX = e.getX(pointerIndex)
                    val touchY = e.getY(pointerIndex)
                    val distanceToCenter = sqrt((midX - touchX).pow(2) + (midY - touchY).pow(2))
                    if (distanceToCenter < mainCircleRadius * 2 / 5) {
                        if (doubleTouchToEnable) {
                            knobEnable = !knobEnable
                            knobEnableListener?.onKnobEnableChanged(knobEnable, currentProgress)
                            invalidate()
                        }
                    }
                    return super.onDoubleTap(e)
                }

                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    val pointerIndex = e.actionIndex
                    val touchX = e.getX(pointerIndex)
                    val touchY = e.getY(pointerIndex)
                    val distanceToCenter = sqrt((midX - touchX).pow(2) + (midY - touchY).pow(2))
                    if (distanceToCenter < mainCircleRadius * 2 / 5) {
                        if (touchToEnable) {
                            knobEnable = !knobEnable
                            knobEnableListener?.onKnobEnableChanged(knobEnable, currentProgress)
                            invalidate()
                        }
                    }
                    return super.onSingleTapConfirmed(e)
                }

            })

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
            borderWidth = typedArray.getDimension(
                R.styleable.RotaryKnob_border_width, borderWidth
            )


            // progress normal
            val progressStyleOrdinal =
                typedArray.getInt(R.styleable.RotaryKnob_progress_style, progressStyle.ordinal)
            progressStyle = SizeStyle.values()[progressStyleOrdinal]
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
            indicatorStyle = SizeStyle.values()[indicatorStyleOrdinal]
            indicatorColor = typedArray.getColor(
                R.styleable.RotaryKnob_indicator_color, indicatorColor
            )
            indicatorSize = typedArray.getDimension(
                R.styleable.RotaryKnob_indicator_size, indicatorSize
            )


            // progress text
            showProgressText =
                typedArray.getBoolean(R.styleable.RotaryKnob_show_progress_text, showProgressText)
            progressText = typedArray.getString(R.styleable.RotaryKnob_progress_text).toString()
            progressTextColor = typedArray.getColor(
                R.styleable.RotaryKnob_progress_text_color, progressTextColor
            )
            progressTextSize = typedArray.getDimension(
                R.styleable.RotaryKnob_progress_text_size, progressTextSize
            )
            val progressTextStyleOrdinal = typedArray.getInt(
                R.styleable.RotaryKnob_progress_text_style, progressTextStyle.ordinal
            )
            progressTextStyle = TextStyle.values()[progressTextStyleOrdinal]

            val progressFontID =
                typedArray.getResourceId(R.styleable.RotaryKnob_progress_text_font, 0)
            if (progressFontID != 0) {
                progressTextFont = ResourcesCompat.getFont(context, progressFontID)
            }


            // suffix text
            showSuffixText =
                typedArray.getBoolean(R.styleable.RotaryKnob_show_suffix_text, showSuffixText)

            val suffixTextTemp = typedArray.getString(R.styleable.RotaryKnob_suffix_text)

            suffixTextTemp?.let {
                suffixText = it
            }


            suffixTextColor = typedArray.getColor(
                R.styleable.RotaryKnob_suffix_text_color, suffixTextColor
            )
            suffixTextSize = typedArray.getDimension(
                R.styleable.RotaryKnob_suffix_text_size, suffixTextSize
            )

            val suffixTextStyleOrdinal =
                typedArray.getInt(R.styleable.RotaryKnob_suffix_text_style, suffixTextStyle.ordinal)
            suffixTextStyle = TextStyle.values()[suffixTextStyleOrdinal]

            val suffixFontID = typedArray.getResourceId(R.styleable.RotaryKnob_suffix_text_font, 0)
            if (suffixFontID != 0) {
                suffixTextFont = ResourcesCompat.getFont(context, suffixFontID)
            }

            // Label
            showLabel = typedArray.getBoolean(R.styleable.RotaryKnob_show_label, showLabel)
            typedArray.getString(R.styleable.RotaryKnob_label_text)?.let {
                labelText = it
            }
            labelTextColor = typedArray.getColor(
                R.styleable.RotaryKnob_label_text_color, labelTextColor
            )
            labelTextSize = typedArray.getDimension(
                R.styleable.RotaryKnob_label_text_size, labelTextSize
            )
            val labelTextStyleOrdinal =
                typedArray.getInt(R.styleable.RotaryKnob_label_text_style, labelTextStyle.ordinal)
            labelTextStyle = TextStyle.values()[labelTextStyleOrdinal]

            val labelFontID = typedArray.getResourceId(R.styleable.RotaryKnob_label_text_font, 0)
            if (labelFontID != 0) {
                labelTextFont = ResourcesCompat.getFont(context, labelFontID)
            }


            labelMargin = typedArray.getDimension(
                R.styleable.RotaryKnob_label_margin, labelMargin
            )

            // enabled
            knobEnable = typedArray.getBoolean(R.styleable.RotaryKnob_knob_enable, knobEnable)
            touchToEnable =
                typedArray.getBoolean(R.styleable.RotaryKnob_touch_to_enable, touchToEnable)
            doubleTouchToEnable = typedArray.getBoolean(
                R.styleable.RotaryKnob_double_touch_to_enable, doubleTouchToEnable
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

            // Get knob image resource, if any
            knobImageID = typedArray.getResourceId(R.styleable.RotaryKnob_knob_image, 0)
            isKnobRotate = typedArray.getBoolean(R.styleable.RotaryKnob_knob_image_rotation, isKnobRotate)
            // value
            min = typedArray.getInt(R.styleable.RotaryKnob_min, min)
            max = typedArray.getInt(R.styleable.RotaryKnob_max, max)
            currentProgress = typedArray.getInt(R.styleable.RotaryKnob_current_progress, min + 1)


        } finally {
            typedArray.recycle()
        }


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

            borderRadius = if (borderWidth == 0f) {
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
            borderRadius = mainCircleRadius

            // progress circle radius.
            progressRadius = radius * (95f / 100)

        }


    }

    override fun onDraw(canvas: Canvas) {

        progressChangeListener?.onProgressChanged(currentProgress)

        canvas.save()

        calculateAreas()

        drawProgressSteps(canvas)

        drawProgressStepsFilled(canvas)

        if (knobImageID == 0) {
            drawBorder(canvas)
            drawCircle(canvas)
        } else {
            drawKnobImage(canvas)
        }

        drawIndicator(canvas)

        drawLabel(canvas)

        drawProgressText(canvas)

        canvas.restore()

    }


    private fun drawProgressSteps(canvas: Canvas) {

        canvas.save()

        setupProgressPaint()


        val largerDotIndices = mutableListOf<Int>()
        if (showBigProgress) {
            for (i in min..<max) {
                if (bigProgressDiff <= 0) {
                    continue
                }
                if ((i + 1) % bigProgressDiff == 0) {
                    if (i == max - 1 && bigProgressDiff > 2) {
                        continue
                    }
                    largerDotIndices.add(i)
                }
            }
        }

        for (i in min until max) {
            val progress = (i - min).toFloat() / (max - 1 - min)

            val angle = 360f - (endOffset + progress * sweepAngle)

            val x = midX + (progressRadius * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y = midY + (progressRadius * cos(Math.toRadians(angle.toDouble()))).toFloat()


            if (progressStyle == SizeStyle.CIRCLE) {

                var dotSize: Float

                if (showBigProgress) {
                    if (i in largerDotIndices) {

                        dotSize = if (bigProgressMultiplier == 0f) {
                            progressRadius / 15 * (20f / (max - min))
                        } else {
                            progressRadius / 30 * (20f / (max - min)) * bigProgressMultiplier
                        }

                        largerDotSize = dotSize

                    } else {
                        dotSize = progressRadius / 30 * (20f / (max - min))
                        normalDotSize = dotSize
                    }


                } else {
                    dotSize = progressRadius / 30 * (20f / (max - min))
                    normalDotSize = dotSize
                }

                canvas.drawCircle(x, y, dotSize, progressPaint)

            } else {

                var lineSize: Float

                if (i in largerDotIndices) {
                    lineSize = progressRadius / 10 * (20f / (max - min))
                    progressPaint.strokeWidth = if (bigProgressMultiplier == 0f) {
                        progressRadius / 20 * (20f / (max - min))
                    } else {
                        progressRadius / 40 * (20f / (max - min)) * bigProgressMultiplier
                    }

                } else {
                    lineSize = progressRadius / 20 * (20f / (max - min))
                    progressPaint.strokeWidth = progressRadius / 40 * (20f / (max - min))
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

        // If a knob image is used, initialize it.
        if (knobImageID != 0) {
            val resKnobImage = BitmapFactory.decodeResource(resources, knobImageID)
            knobImage = Bitmap.createScaledBitmap(
                resKnobImage,
                (mainCircleRadius * 2).roundToInt(),
                (mainCircleRadius * 2).roundToInt(),
                true
            )
        }

        canvas.restore()

    }

    private fun drawProgressStepsFilled(canvas: Canvas) {

        canvas.save()

        setupProgressFilledPaint()

        val largerDotIndices = mutableListOf<Int>()
        if (showBigProgress) {
            for (i in min..<max) {
                if (bigProgressDiff <= 0) {
                    continue
                }
                if ((i + 1) % bigProgressDiff == 0) {
                    if (i == max) {
                        continue
                    }
                    largerDotIndices.add(i)
                }
            }
        }



        for (i in min..<currentProgress) {
            val progress = (i - min).toFloat() / (max - 1 - min)

            val angle = 360f - (endOffset + progress * sweepAngle)

            val x = midX + (progressRadius * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y = midY + (progressRadius * cos(Math.toRadians(angle.toDouble()))).toFloat()

            if (progressStyle == SizeStyle.CIRCLE) {

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

                canvas.drawCircle(x, y, dotSize, progressFilled)

            } else {

                var lineSize: Float

                if (i in largerDotIndices) {

                    if (progressFilledMultiplier == 0f) {
                        lineSize = progressRadius / 10 * (25f / (max - min))
                        progressFilled.strokeWidth = progressRadius / 20 * (25f / (max - min))
                    } else {
                        lineSize = progressRadius / 10 * (25f / (max - min))
                        progressFilled.strokeWidth =
                            progressRadius / 20 * (25f / (max - min)) * progressFilledMultiplier
                    }


                } else {
                    if (progressFilledMultiplier == 0f) {
                        lineSize = progressRadius / 20 * (25f / (max - min))
                        progressFilled.strokeWidth = progressRadius / 40 * (25f / (max - min))
                    } else {
                        lineSize = progressRadius / 20 * (25f / (max - min))
                        progressFilled.strokeWidth =
                            progressRadius / 40 * (25f / (max - min)) * progressFilledMultiplier
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

    private fun drawBorder(canvas: Canvas) {

        canvas.save()

        setupBorderPaint()

        canvas.drawCircle(midX, midY, borderRadius, borderPaint)

        canvas.restore()

    }

    private fun drawKnobImage(canvas: Canvas) {

        canvas.save()

        setupCirclePaint()

        val progress1 = (currentProgress - min - 1).toFloat() / (max - 1 - min)
        val angle = (endOffset + progress1 * sweepAngle)

        knobImage.let {
            if (isKnobRotate) {
                val matrix = Matrix()
                matrix.postTranslate(
                    midX.toFloat() - mainCircleRadius,
                    midY.toFloat() - mainCircleRadius
                )
                matrix.postRotate(angle, midX.toFloat(), midY.toFloat())
                canvas.drawBitmap(knobImage, matrix, knobImagePaint)
            } else {
                canvas.drawBitmap(knobImage, midX.toFloat() - mainCircleRadius, midY.toFloat() - mainCircleRadius, knobImagePaint)
            }
        }

        canvas.restore()

    }

    private fun drawCircle(canvas: Canvas) {

        canvas.save()

        setupCirclePaint()

        canvas.drawCircle(midX, midY, mainCircleRadius, circlePaint)

        canvas.restore()


    }

    private fun drawIndicator(canvas: Canvas) {
        canvas.save()
        setupDrawIndicator()
        val progress1 = (currentProgress - min - 1).toFloat() / (max - 1 - min)
        val angle = 360f - (endOffset + progress1 * sweepAngle)
        if (indicatorStyle == SizeStyle.CIRCLE) {
            val x =
                midX + (mainCircleRadius * 2 / 3 * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val y =
                midY + (mainCircleRadius * 2 / 3 * cos(Math.toRadians(angle.toDouble()))).toFloat()

            val mIndicatorSize: Float = if (indicatorSize == 0f) {
                mainCircleRadius / 8
            } else {
                indicatorSize
            }
            canvas.drawCircle(x, y, mIndicatorSize, indicatorPaint)
        } else {
            val startMargin = radius * (2f / 5) // Starting from 2/5 radius margin
            val indicatorStartX =
                midX + (startMargin * sin(Math.toRadians(angle.toDouble()))).toFloat()
            val indicatorStartY =
                midY + (startMargin * cos(Math.toRadians(angle.toDouble()))).toFloat()
            val indicatorLength = if (indicatorSize == 0f) {
                radius / 5
            } else {
                indicatorSize
            }
            val indicatorEndX =
                midX + (startMargin + indicatorLength) * sin(Math.toRadians(angle.toDouble())).toFloat()
            val indicatorEndY =
                midY + (startMargin + indicatorLength) * cos(Math.toRadians(angle.toDouble())).toFloat()
            canvas.drawLine(
                indicatorStartX, indicatorStartY, indicatorEndX, indicatorEndY, indicatorPaint
            )

        }
        canvas.restore()
    }

    private fun drawLabel(canvas: Canvas) {

        if (!showLabel || labelText.isBlank() || labelText == "") return

        canvas.save()

        setupLabelPaint()

        val textWidth = labelPaint.measureText(labelText)
//        val textHeight = labelPaint.descent() - labelPaint.ascent()
        val textX = midX - textWidth / 2
        val textY = if (labelMargin == 0f) {
            midY + radius
        } else {
            midY + borderRadius + labelMargin
        }
        canvas.drawText(labelText, textX, textY, labelPaint)
        canvas.restore()
    }

    private fun drawProgressText(canvas: Canvas) {

        canvas.save()

        setupTextPaint()


        val progressText = "$currentProgress "
        val progressTextWidth = textPaint.measureText(progressText)
        val progressTextHeight = textPaint.descent() - textPaint.ascent()
        val progressTextX = midX - progressTextWidth / 2
        val progressTextY = midY + progressTextHeight / 3f

        if (showProgressText) {
            canvas.drawText(progressText, progressTextX, progressTextY, textPaint)
        }

        val additionalText: String = suffixText.ifEmpty {
            "%"
        }

//        val additionalTextWidth = suffixTextPaint.measureText(additionalText)
//        val additionalTextHeight = suffixTextPaint.descent() - suffixTextPaint.ascent()
        val additionalTextX = midX + progressTextWidth * 5 / 12
        val additionalTextY = progressTextY - progressTextHeight / 2

        if (showSuffixText) {
            canvas.drawText(additionalText, additionalTextX, additionalTextY, suffixTextPaint)
        }

        canvas.restore()

    }


    private fun setupProgressPaint() {

        progressPaint.reset()

        progressPaint.isAntiAlias = true
        progressPaint.color = if (knobEnable) {
            progressColor
        } else {
            disabledProgressColor
        }
        progressPaint.style = Paint.Style.FILL

    }

    private fun setupProgressFilledPaint() {

        progressFilled.reset()

        progressFilled.isAntiAlias = true
        progressFilled.color = if (knobEnable) {
            progressFilledColor
        } else {
            disabledProgressFilledColor
        }
        progressFilled.style = Paint.Style.FILL
    }

    private fun setupBorderPaint() {

        borderPaint.reset()

        borderPaint.isAntiAlias = true
        borderPaint.color = if (knobEnable) {
            borderColor
        } else {
            disabledBorderColor
        }
        borderPaint.style = Paint.Style.FILL
    }

    private fun setupCirclePaint() {

        circlePaint.isAntiAlias = true

        circlePaint.reset()

        if (circleStyle == CircleStyle.SOLID) {
            circlePaint.color = if (knobEnable) {
                circleColor
            } else {
                disabledCircleColor
            }
            circlePaint.style = Paint.Style.FILL
        } else {

            val cColor = if (knobEnable) {
                circleGradientCenterColor
            } else {
                disabledCircleGradientCenterColor
            }
            val oColor = if (knobEnable) {
                circleGradientOuterColor
            } else {
                disabledCircleGradientOuterColor
            }

            val radius = mainCircleRadius
            val gradient = RadialGradient(
                midX, midY, radius, cColor, oColor, Shader.TileMode.CLAMP
            )
            circlePaint.shader = gradient
        }

    }

    private fun setupDrawIndicator() {

        indicatorPaint.reset()

        indicatorPaint.isAntiAlias = true
        indicatorPaint.color = if (knobEnable) {
            indicatorColor
        } else {
            disabledIndicatorColor
        }
        indicatorPaint.style = Paint.Style.FILL
        indicatorPaint.strokeWidth = 7f
    }

    private fun setupLabelPaint() {

        labelPaint.reset()

        labelPaint.isAntiAlias = true
        labelPaint.color = if (knobEnable) {
            labelTextColor
        } else {
            disabledLabelTextColor
        }
        labelPaint.style = Paint.Style.FILL
        labelPaint.textSize = labelTextSize
        labelPaint.typeface = when (labelTextStyle) {
            TextStyle.NORMAL -> Typeface.DEFAULT
            TextStyle.BOLD -> Typeface.DEFAULT_BOLD
            TextStyle.ITALIC -> Typeface.defaultFromStyle(Typeface.ITALIC)
            TextStyle.BOLD_ITALIC -> Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
        }
        if (labelTextFont != null) {
            labelPaint.typeface = labelTextFont
        }

    }

    private fun setupTextPaint() {

        textPaint.reset()

        textPaint.isAntiAlias = true
        textPaint.color = if (knobEnable) {
            progressTextColor
        } else {
            disabledProgressTextColor
        }
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = progressTextSize
        textPaint.typeface = when (progressTextStyle) {
            TextStyle.NORMAL -> Typeface.DEFAULT
            TextStyle.BOLD -> Typeface.DEFAULT_BOLD
            TextStyle.ITALIC -> Typeface.defaultFromStyle(Typeface.ITALIC)
            TextStyle.BOLD_ITALIC -> Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
        }
        if (progressTextFont != null) {
            textPaint.typeface = progressTextFont
        }

        suffixTextPaint.isAntiAlias = true
        suffixTextPaint.color = if (knobEnable) {
            suffixTextColor
        } else {
            disabledsuffixTextColor
        }
        suffixTextPaint.style = Paint.Style.FILL
        suffixTextPaint.textSize = suffixTextSize
        suffixTextPaint.typeface = when (suffixTextStyle) {
            TextStyle.NORMAL -> Typeface.DEFAULT
            TextStyle.BOLD -> Typeface.DEFAULT_BOLD
            TextStyle.ITALIC -> Typeface.defaultFromStyle(Typeface.ITALIC)
            TextStyle.BOLD_ITALIC -> Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
        }
        if (suffixTextFont != null) {
            suffixTextPaint.typeface = suffixTextFont
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val handled = gestureDetector?.onTouchEvent(event) ?: false
        if (handled) {
            return true
        }


        val action = event.actionMasked
        val pointerIndex = event.actionIndex

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {

            val touchX = event.getX(pointerIndex)
            val touchY = event.getY(pointerIndex)


            val distanceToCenter = sqrt((midX - touchX).pow(2) + (midY - touchY).pow(2))


            if (distanceToCenter in (mainCircleRadius - 5)..radius) {

                if (!knobEnable) {
                    return false
                }

                parent.requestDisallowInterceptTouchEvent(true)


                val dx = touchX - midX
                val dy = touchY - midY

                var currentAngle = (atan2(dy.toDouble(), dx.toDouble()) * 180 / Math.PI).toFloat()

                currentAngle -= 90

                currentAngle -= startOffset

                if (currentAngle < 0) {
                    currentAngle += 360
                }

                val reVerseAngle = 360 - currentAngle

                val temp1 = (reVerseAngle - 360) / sweepAngle

                var temp = -(temp1 * (max - min)).toInt()

                temp += min + 1

                return if (temp in min..<max + 1) {
                    currentProgress = temp
                    invalidate()
                    true
                } else {
                    false
                }


            }
            return true

        } else if (action == MotionEvent.ACTION_MOVE) {

            if (!knobEnable) {
                return false
            }

            val touchX = event.getX(pointerIndex)
            val touchY = event.getY(pointerIndex)

            val distanceToCenter = sqrt((midX - touchX).pow(2) + (midY - touchY).pow(2))

            startingRadius = if (startingRadius == 0f) distanceToCenter else startingRadius


            if (startingRadius < radius && startingRadius > mainCircleRadius * 3 / 5) {

                val dx = touchX - midX
                val dy = touchY - midY

                var currentAngle = (atan2(dy.toDouble(), dx.toDouble()) * 180 / Math.PI).toFloat()

                currentAngle -= 90

                currentAngle -= startOffset

                if (currentAngle < 0) {
                    currentAngle += 360
                }

                val reVerseAngle = 360 - currentAngle

                val temp1 = (reVerseAngle - 360) / sweepAngle

                var temp = -(temp1 * (max - min)).toInt()
                temp += min + 1

                return if (temp in min..<max + 1) {
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

    private fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    interface OnProgressChangeListener {
        fun onProgressChanged(progress: Int)
    }

    interface OnKnobEnableListener {
        fun onKnobEnableChanged(isEnable: Boolean, progress: Int)
    }

}