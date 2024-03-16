package com.rejowan.rotaryknobsample

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.rejowan.rotaryknob.RotaryKnob
import com.rejowan.rotaryknobsample.ColorPicker.Companion.colorIntToHex
import com.rejowan.rotaryknobsample.databinding.ActivityKnobPlaygroundBinding


class KnobPlayground : AppCompatActivity() {

    private val binding by lazy { ActivityKnobPlaygroundBinding.inflate(layoutInflater) }

    private var isCircleArrowEnable = false
    private var isBorderEnable = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        arrowMethods()

        circleStyles()

        borderStyles()


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
        isBorderEnable = binding.borderContentLayout.visibility == android.view.View.VISIBLE


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



    }

    private fun showHideBorderLayout() {

        isBorderEnable = !isBorderEnable
        binding.borderArrow.setImageResource(if (isBorderEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
        binding.borderContentLayout.visibility =
            if (isBorderEnable) android.view.View.VISIBLE else android.view.View.GONE

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