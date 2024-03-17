package com.rejowan.rotaryknobsample

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.rejowan.rotaryknob.RotaryKnob
import com.rejowan.rotaryknobsample.ColorPicker.Companion.colorIntToHex
import com.rejowan.rotaryknobsample.databinding.ActivityKnobPlaygroundBinding


class KnobPlayground : AppCompatActivity() {

    private val binding by lazy { ActivityKnobPlaygroundBinding.inflate(layoutInflater) }

    private var isCircleArrowEnable = false
    private var isBorderArrowEnable = false
    private var isProgressArrowEnable = false
    private var isProgressFilledArrowEnable = false
    private var isIndicatorArrowEnable = false
    private var isProgressTextArrowEnable = false
    private var isSuffixTextArrowEnable = false
    private var isLabelArrowEnable = false
    private var isEnabledArrowEnable = false
    private var isValueArrowEnable = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        arrowMethods()

        circleStyles()

        borderStyles()

        progressStyles()

        progressFilledStyles()

        indicatorStyles()

        progressTextStyles()

        suffixTextStyles()

        labelTextStyles()

        enableStyles()

        valueStyles()


    }

    private fun valueStyles() {

        binding.minValueEditText.setText(binding.rotaryKnob.min.toString())
        binding.minValueEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val value = binding.minValueEditText.text.toString().toIntOrNull() ?: 0
                binding.rotaryKnob.min = value
            }
        }

        binding.maxValueEditText.setText(binding.rotaryKnob.max.toString())
        binding.maxValueEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val value = binding.maxValueEditText.text.toString().toIntOrNull() ?: 100
                binding.rotaryKnob.max = value
            }
        }

        binding.currentProgressEditText.setText(binding.rotaryKnob.currentProgress.toString())
        binding.currentProgressEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val value = binding.currentProgressEditText.text.toString().toIntOrNull() ?: 0
                binding.rotaryKnob.currentProgress = value
            }
        }


    }

    private fun enableStyles() {

        binding.enableKnobSwitch.isChecked = binding.rotaryKnob.isEnabled
        binding.enableKnobSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.knobEnable = isChecked
        }

        binding.touchToEnableSwitch.isChecked = binding.rotaryKnob.touchToEnable
        binding.touchToEnableSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.touchToEnable = isChecked
        }

        binding.doubleTouchToEnableSwitch.isChecked = binding.rotaryKnob.doubleTouchToEnable
        binding.doubleTouchToEnableSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.doubleTouchToEnable = isChecked
        }

        binding.disabledCircleColorView.setBackgroundColor(binding.rotaryKnob.disabledCircleColor)
        binding.disabledCircleColorView.text = colorIntToHex(binding.rotaryKnob.disabledCircleColor)

        binding.disabledCircleColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledCircleColor = colorInt
                    binding.disabledCircleColorView.setBackgroundColor(colorInt)
                    binding.disabledCircleColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledCircleGradientCenterColorView.setBackgroundColor(binding.rotaryKnob.disabledCircleGradientCenterColor)
        binding.disabledCircleGradientCenterColorView.text =
            colorIntToHex(binding.rotaryKnob.disabledCircleGradientCenterColor)

        binding.disabledCircleGradientCenterColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledCircleGradientCenterColor = colorInt
                    binding.disabledCircleGradientCenterColorView.setBackgroundColor(colorInt)
                    binding.disabledCircleGradientCenterColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledCircleGradientOuterColorView.setBackgroundColor(binding.rotaryKnob.disabledCircleGradientOuterColor)
        binding.disabledCircleGradientOuterColorView.text =
            colorIntToHex(binding.rotaryKnob.disabledCircleGradientOuterColor)

        binding.disabledCircleGradientOuterColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledCircleGradientOuterColor = colorInt
                    binding.disabledCircleGradientOuterColorView.setBackgroundColor(colorInt)
                    binding.disabledCircleGradientOuterColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledBorderColorView.setBackgroundColor(binding.rotaryKnob.disabledBorderColor)
        binding.disabledBorderColorView.text = colorIntToHex(binding.rotaryKnob.disabledBorderColor)

        binding.disabledBorderColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledBorderColor = colorInt
                    binding.disabledBorderColorView.setBackgroundColor(colorInt)
                    binding.disabledBorderColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledProgressColorView.setBackgroundColor(binding.rotaryKnob.disabledProgressColor)
        binding.disabledProgressColorView.text =
            colorIntToHex(binding.rotaryKnob.disabledProgressColor)

        binding.disabledProgressColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledProgressColor = colorInt
                    binding.disabledProgressColorView.setBackgroundColor(colorInt)
                    binding.disabledProgressColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledBigProgressColorView.setBackgroundColor(binding.rotaryKnob.disabledBigProgressColor)
        binding.disabledBigProgressColorView.text =
            colorIntToHex(binding.rotaryKnob.disabledBigProgressColor)

        binding.disabledBigProgressColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledBigProgressColor = colorInt
                    binding.disabledBigProgressColorView.setBackgroundColor(colorInt)
                    binding.disabledBigProgressColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledProgressFilledColorView.setBackgroundColor(binding.rotaryKnob.disabledProgressFilledColor)
        binding.disabledProgressFilledColorView.text =
            colorIntToHex(binding.rotaryKnob.disabledProgressFilledColor)

        binding.disabledProgressFilledColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledProgressFilledColor = colorInt
                    binding.disabledProgressFilledColorView.setBackgroundColor(colorInt)
                    binding.disabledProgressFilledColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledIndicatorColorView.setBackgroundColor(binding.rotaryKnob.disabledIndicatorColor)
        binding.disabledIndicatorColorView.text =
            colorIntToHex(binding.rotaryKnob.disabledIndicatorColor)

        binding.disabledIndicatorColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledIndicatorColor = colorInt
                    binding.disabledIndicatorColorView.setBackgroundColor(colorInt)
                    binding.disabledIndicatorColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledProgressTextColorView.setBackgroundColor(binding.rotaryKnob.disabledProgressTextColor)
        binding.disabledProgressTextColorView.text =
            colorIntToHex(binding.rotaryKnob.disabledProgressTextColor)

        binding.disabledProgressTextColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledProgressTextColor = colorInt
                    binding.disabledProgressTextColorView.setBackgroundColor(colorInt)
                    binding.disabledProgressTextColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledSuffixTextColorView.setBackgroundColor(binding.rotaryKnob.disabledsuffixTextColor)
        binding.disabledSuffixTextColorView.text =
            colorIntToHex(binding.rotaryKnob.disabledsuffixTextColor)

        binding.disabledSuffixTextColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledsuffixTextColor = colorInt
                    binding.disabledSuffixTextColorView.setBackgroundColor(colorInt)
                    binding.disabledSuffixTextColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.disabledLabelTextColorView.setBackgroundColor(binding.rotaryKnob.disabledLabelTextColor)
        binding.disabledLabelTextColorView.text =
            colorIntToHex(binding.rotaryKnob.disabledLabelTextColor)

        binding.disabledLabelTextColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.disabledLabelTextColor = colorInt
                    binding.disabledLabelTextColorView.setBackgroundColor(colorInt)
                    binding.disabledLabelTextColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }


    }

    private fun labelTextStyles() {

        binding.showLabelTextSwitch.isChecked = binding.rotaryKnob.showLabel
        binding.showLabelTextSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.showLabel = isChecked
        }

        binding.labelTextColorView.setBackgroundColor(binding.rotaryKnob.labelTextColor)
        binding.labelTextColorView.text = colorIntToHex(binding.rotaryKnob.labelTextColor)

        binding.labelTextColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.labelTextColor = colorInt
                    binding.labelTextColorView.setBackgroundColor(colorInt)
                    binding.labelTextColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.labelTextSizeEditText.setText(binding.rotaryKnob.labelTextSize.toString())
        binding.labelTextSizeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val size = binding.labelTextSizeEditText.text.toString().toFloatOrNull() ?: 0f
                binding.rotaryKnob.labelTextSize = size
            }
        }

        binding.labelTextEditText.setText(binding.rotaryKnob.labelText)
        binding.labelTextEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding.labelTextEditText.text.toString()
                binding.rotaryKnob.labelText = text
            }
        }

        val listOfStyles = RotaryKnob.TextStyle.values().map { it.name }
        val adapter =
            android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfStyles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.labelTextStyleSpinner.adapter = adapter
        binding.labelTextStyleSpinner.setSelection(binding.rotaryKnob.labelTextStyle.ordinal)

        binding.labelTextStyleSpinner.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    val style = RotaryKnob.TextStyle.values()[position]
                    binding.rotaryKnob.labelTextStyle = style

                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                }

            }

        binding.setLabelCustomFontSwitch.isChecked = false
        binding.setLabelCustomFontSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.labelTextFont = if (isChecked) {
                ResourcesCompat.getFont(this, R.font.ubuntu_normal)
            } else {
                null
            }

        }

        binding.labelMarginEditText.setText(binding.rotaryKnob.labelMargin.toString())
        binding.labelMarginEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val margin = binding.labelMarginEditText.text.toString().toFloatOrNull() ?: 0f
                binding.rotaryKnob.labelMargin = margin
            }
        }


    }

    private fun suffixTextStyles() {

        binding.showSuffixTextSwitch.isChecked = binding.rotaryKnob.showSuffixText
        binding.showSuffixTextSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.showSuffixText = isChecked
        }

        binding.suffixTextColorView.setBackgroundColor(binding.rotaryKnob.suffixTextColor)
        binding.suffixTextColorView.text = colorIntToHex(binding.rotaryKnob.suffixTextColor)

        binding.suffixTextColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.suffixTextColor = colorInt
                    binding.suffixTextColorView.setBackgroundColor(colorInt)
                    binding.suffixTextColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.suffixTextSizeEditText.setText(binding.rotaryKnob.suffixTextSize.toString())
        binding.suffixTextSizeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val size = binding.suffixTextSizeEditText.text.toString().toFloatOrNull() ?: 0f
                binding.rotaryKnob.suffixTextSize = size
            }
        }

        binding.suffixTextEditText.setText(binding.rotaryKnob.suffixText)
        binding.suffixTextEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding.suffixTextEditText.text.toString()
                binding.rotaryKnob.suffixText = text
            }
        }

        val listOfStyles = RotaryKnob.TextStyle.values().map { it.name }
        val adapter =
            android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfStyles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.suffixTextStyleSpinner.adapter = adapter
        binding.suffixTextStyleSpinner.setSelection(binding.rotaryKnob.suffixTextStyle.ordinal)

        binding.suffixTextStyleSpinner.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    val style = RotaryKnob.TextStyle.values()[position]
                    binding.rotaryKnob.suffixTextStyle = style

                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                }

            }

        binding.setSuffixCustomFontSwitch.isChecked = false
        binding.setSuffixCustomFontSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.suffixTextFont = if (isChecked) {
                ResourcesCompat.getFont(this, R.font.ubuntu_normal)
            } else {
                null
            }

        }


    }

    private fun progressTextStyles() {

        binding.showProgressTextSwitch.isChecked = binding.rotaryKnob.showProgressText
        binding.showProgressTextSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.showProgressText = isChecked
        }

        binding.progressTextColorView.setBackgroundColor(binding.rotaryKnob.progressTextColor)
        binding.progressTextColorView.text = colorIntToHex(binding.rotaryKnob.progressTextColor)

        binding.progressTextColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.progressTextColor = colorInt
                    binding.progressTextColorView.setBackgroundColor(colorInt)
                    binding.progressTextColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.progressTextSizeEditText.setText(binding.rotaryKnob.progressTextSize.toString())
        binding.progressTextSizeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val size = binding.progressTextSizeEditText.text.toString().toFloatOrNull() ?: 0f
                binding.rotaryKnob.progressTextSize = size
            }
        }

        val listOfStyles = RotaryKnob.TextStyle.values().map { it.name }
        val adapter =
            android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfStyles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.progressTextStyleSpinner.adapter = adapter
        binding.progressTextStyleSpinner.setSelection(binding.rotaryKnob.progressTextStyle.ordinal)

        binding.progressTextStyleSpinner.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    val style = RotaryKnob.TextStyle.values()[position]
                    binding.rotaryKnob.progressTextStyle = style

                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                }

            }

        binding.setCustomFontSwitch.isChecked = false
        binding.setCustomFontSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.progressTextFont = if (isChecked) {
                ResourcesCompat.getFont(this, R.font.ubuntu_normal)
            } else {
                null
            }

        }

    }

    private fun indicatorStyles() {

        val listOfStyles = RotaryKnob.SizeStyle.values().map { it.name }
        val adapter =
            android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfStyles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.indicatorStyleSpinner.adapter = adapter
        binding.indicatorStyleSpinner.setSelection(binding.rotaryKnob.indicatorStyle.ordinal)

        binding.indicatorStyleSpinner.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    val style = RotaryKnob.SizeStyle.values()[position]
                    binding.rotaryKnob.indicatorStyle = style

                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                }

            }

        binding.indicatorColorView.setBackgroundColor(binding.rotaryKnob.indicatorColor)
        binding.indicatorColorView.text = colorIntToHex(binding.rotaryKnob.indicatorColor)

        binding.indicatorColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.indicatorColor = colorInt
                    binding.indicatorColorView.setBackgroundColor(colorInt)
                    binding.indicatorColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.indicatorSizeEditText.setText(binding.rotaryKnob.indicatorSize.toString())
        binding.indicatorSizeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val size = binding.indicatorSizeEditText.text.toString().toFloatOrNull() ?: 0f
                binding.rotaryKnob.indicatorSize = size
            }
        }


    }

    private fun progressFilledStyles() {

        binding.progressFilledColorView.setBackgroundColor(binding.rotaryKnob.progressFilledColor)
        binding.progressFilledColorView.text = colorIntToHex(binding.rotaryKnob.progressFilledColor)

        binding.progressFilledColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.progressFilledColor = colorInt
                    binding.progressFilledColorView.setBackgroundColor(colorInt)
                    binding.progressFilledColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.progressFilledMultiplierEditText.setText(binding.rotaryKnob.progressFilledMultiplier.toString())
        binding.progressFilledMultiplierEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val multiplier =
                    binding.progressFilledMultiplierEditText.text.toString().toFloatOrNull() ?: 1f
                binding.rotaryKnob.progressFilledMultiplier = multiplier
            }
        }


    }

    private fun progressStyles() {

        val listOfStyles = RotaryKnob.SizeStyle.values().map { it.name }
        val adapter =
            android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfStyles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.progressStyleSpinner.adapter = adapter
        binding.progressStyleSpinner.setSelection(binding.rotaryKnob.progressStyle.ordinal)

        binding.progressStyleSpinner.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    val style = RotaryKnob.SizeStyle.values()[position]
                    binding.rotaryKnob.progressStyle = style

                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                }

            }

        binding.progressColorView.setBackgroundColor(binding.rotaryKnob.progressColor)
        binding.progressColorView.text = colorIntToHex(binding.rotaryKnob.progressColor)

        binding.progressColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.progressColor = colorInt
                    binding.progressColorView.setBackgroundColor(colorInt)
                    binding.progressColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.showBigProgressSwitch.isChecked = binding.rotaryKnob.showBigProgress
        binding.showBigProgressSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.showBigProgress = isChecked
        }

        binding.bigProgressMultiplierEditText.setText(binding.rotaryKnob.bigProgressMultiplier.toString())
        binding.bigProgressMultiplierEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val multiplier =
                    binding.bigProgressMultiplierEditText.text.toString().toFloatOrNull() ?: 1f
                binding.rotaryKnob.bigProgressMultiplier = multiplier
            }
        }

        binding.bigProgressDiffEditText.setText(binding.rotaryKnob.bigProgressDiff.toString())
        binding.bigProgressDiffEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val diff = binding.bigProgressDiffEditText.text.toString().toIntOrNull() ?: 0
                binding.rotaryKnob.bigProgressDiff = diff
            }
        }


    }

    private fun borderStyles() {

        binding.borderSwitch.isChecked = binding.rotaryKnob.showBorder
        binding.borderSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.rotaryKnob.showBorder = isChecked
        }

        binding.borderColorView.setBackgroundColor(binding.rotaryKnob.borderColor)
        binding.borderColorView.text = colorIntToHex(binding.rotaryKnob.borderColor)

        binding.borderColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.borderColor = colorInt
                    binding.borderColorView.setBackgroundColor(colorInt)
                    binding.borderColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.borderWidthEditText.setText(binding.rotaryKnob.borderWidth.toString())
        binding.borderWidthEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val width = binding.borderWidthEditText.text.toString().toFloatOrNull() ?: 0f
                binding.rotaryKnob.borderWidth = width
            }
        }


    }

    private fun circleStyles() {

        val listOfStyles = RotaryKnob.CircleStyle.values().map { it.name }
        val adapter =
            android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfStyles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.circleStyleSpinner.adapter = adapter
        binding.circleStyleSpinner.setSelection(binding.rotaryKnob.circleStyle.ordinal)

        binding.circleStyleSpinner.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    val style = RotaryKnob.CircleStyle.values()[position]
                    binding.rotaryKnob.circleStyle = style

                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                }

            }

        binding.circleColorView.setBackgroundColor(binding.rotaryKnob.circleColor)
        binding.circleColorView.text = colorIntToHex(binding.rotaryKnob.circleColor)
        binding.circleGradientCenterColorView.setBackgroundColor(binding.rotaryKnob.circleGradientCenterColor)
        binding.circleGradientCenterColorView.text =
            colorIntToHex(binding.rotaryKnob.circleGradientCenterColor)
        binding.circleGradientOuterColorView.setBackgroundColor(binding.rotaryKnob.circleGradientOuterColor)
        binding.circleGradientOuterColorView.text =
            colorIntToHex(binding.rotaryKnob.circleGradientOuterColor)

        binding.circleColorView.setOnClickListener {

            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.circleColor = colorInt
                    binding.circleColorView.setBackgroundColor(colorInt)
                    binding.circleColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.circleGradientCenterColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.circleGradientCenterColor = colorInt
                    binding.circleGradientCenterColorView.setBackgroundColor(colorInt)
                    binding.circleGradientCenterColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

        binding.circleGradientOuterColorView.setOnClickListener {
            val colorPicker = ColorPicker()
            colorPicker.setOnColorSelectedListener(object : ColorPicker.OnColorSelectedListener {
                override fun onColorSelected(colorString: String, colorInt: Int) {
                    binding.rotaryKnob.circleGradientOuterColor = colorInt
                    binding.circleGradientOuterColorView.setBackgroundColor(colorInt)
                    binding.circleGradientOuterColorView.text = colorString
                }
            })
            colorPicker.show(supportFragmentManager, "colorPicker")
        }

    }

    private fun arrowMethods() {
        isCircleArrowEnable = binding.circleContentLayout.visibility == android.view.View.VISIBLE
        isBorderArrowEnable = binding.borderContentLayout.visibility == android.view.View.VISIBLE
        isProgressArrowEnable =
            binding.progressContentLayout.visibility == android.view.View.VISIBLE
        isProgressFilledArrowEnable =
            binding.progressFilledContentLayout.visibility == android.view.View.VISIBLE
        isIndicatorArrowEnable =
            binding.indicatorContentLayout.visibility == android.view.View.VISIBLE
        isProgressTextArrowEnable =
            binding.progressTextContentLayout.visibility == android.view.View.VISIBLE
        isSuffixTextArrowEnable =
            binding.suffixTextContentLayout.visibility == android.view.View.VISIBLE
        isLabelArrowEnable = binding.labelTextContentLayout.visibility == android.view.View.VISIBLE
        isEnabledArrowEnable = binding.enableContentLayout.visibility == android.view.View.VISIBLE
        isValueArrowEnable = binding.valueContentLayout.visibility == android.view.View.VISIBLE


        binding.circleArrow.setOnClickListener {
            showHideCircleLayout()
        }

        binding.circleStyleTextView.setOnClickListener {
            showHideCircleLayout()
        }


        binding.borderArrow.setOnClickListener {
            showHideBorderLayout()
        }

        binding.borderStyleTextView.setOnClickListener {
            showHideBorderLayout()
        }

        binding.progressArrow.setOnClickListener {
            showHideProgressLayout()
        }

        binding.progressStyleTextView.setOnClickListener {
            showHideProgressLayout()
        }

        binding.progressFilledArrow.setOnClickListener {
            showHideProgressFilledLayout()
        }

        binding.progressFilledStyleTextView.setOnClickListener {
            showHideProgressFilledLayout()
        }

        binding.indicatorArrow.setOnClickListener {
            showHideIndicatorLayout()
        }

        binding.indicatorStyleTextView.setOnClickListener {
            showHideIndicatorLayout()
        }

        binding.progressTextArrow.setOnClickListener {
            showHideProgressTextLayout()
        }

        binding.progressTextStyleTextView.setOnClickListener {
            showHideProgressTextLayout()
        }

        binding.suffixTextArrow.setOnClickListener {
            showHideSuffixTextLayout()
        }

        binding.suffixTextStyleTextView.setOnClickListener {
            showHideSuffixTextLayout()
        }

        binding.labelTextArrow.setOnClickListener {
            showHideLabelLayout()
        }

        binding.labelTextStyleTextView.setOnClickListener {
            showHideLabelLayout()
        }

        binding.enableArrow.setOnClickListener {
            showHideEnableLayout()
        }

        binding.enableStyleTextView.setOnClickListener {
            showHideEnableLayout()
        }

        binding.valueArrow.setOnClickListener {
            showHideValueLayout()
        }

        binding.valueTextView.setOnClickListener {
            showHideValueLayout()
        }


    }

    private fun showHideValueLayout() {

        isValueArrowEnable = !isValueArrowEnable
        binding.valueArrow.setImageResource(if (isValueArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.valueContentLayout.visibility =
            if (isValueArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showHideEnableLayout() {
        isEnabledArrowEnable = !isEnabledArrowEnable
        binding.enableArrow.setImageResource(if (isEnabledArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.enableContentLayout.visibility =
            if (isEnabledArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showHideLabelLayout() {
        isLabelArrowEnable = !isLabelArrowEnable
        binding.labelTextArrow.setImageResource(if (isLabelArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.labelTextContentLayout.visibility =
            if (isLabelArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showHideSuffixTextLayout() {
        isSuffixTextArrowEnable = !isSuffixTextArrowEnable
        binding.suffixTextArrow.setImageResource(if (isSuffixTextArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.suffixTextContentLayout.visibility =
            if (isSuffixTextArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showHideProgressTextLayout() {
        isProgressTextArrowEnable = !isProgressTextArrowEnable
        binding.progressTextArrow.setImageResource(if (isProgressTextArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.progressTextContentLayout.visibility =
            if (isProgressTextArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showHideIndicatorLayout() {
        isIndicatorArrowEnable = !isIndicatorArrowEnable
        binding.indicatorArrow.setImageResource(if (isIndicatorArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.indicatorContentLayout.visibility =
            if (isIndicatorArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showHideProgressFilledLayout() {
        isProgressFilledArrowEnable = !isProgressFilledArrowEnable
        binding.progressFilledArrow.setImageResource(if (isProgressFilledArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.progressFilledContentLayout.visibility =
            if (isProgressFilledArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showHideProgressLayout() {
        isProgressArrowEnable = !isProgressArrowEnable
        binding.progressArrow.setImageResource(if (isProgressArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.progressContentLayout.visibility =
            if (isProgressArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showHideBorderLayout() {
        isBorderArrowEnable = !isBorderArrowEnable
        binding.borderArrow.setImageResource(if (isBorderArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.borderContentLayout.visibility =
            if (isBorderArrowEnable) android.view.View.VISIBLE else android.view.View.GONE

    }

    private fun showHideCircleLayout() {
        isCircleArrowEnable = !isCircleArrowEnable
        binding.circleArrow.setImageResource(if (isCircleArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.circleContentLayout.visibility =
            if (isCircleArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

}