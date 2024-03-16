package com.rejowan.rotaryknobsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rejowan.rotaryknob.RotaryKnob
import com.rejowan.rotaryknobsample.colorpicker.MaterialColorPickerDialog
import com.rejowan.rotaryknobsample.colorpicker.MaterialColorPickerDialog.ColorShape
import com.rejowan.rotaryknobsample.colorpicker.MaterialColorPickerDialog.ColorSwatch
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
        binding.circleGradientCenterColorView.setBackgroundColor(binding.rotaryKnob.circleGradientCenterColor)
        binding.circleGradientOuterColorView.setBackgroundColor(binding.rotaryKnob.circleGradientOuterColor)

        binding.circleColorView.setOnClickListener {
            MaterialColorPickerDialog
                .Builder(this)
                .setTitle("Choose Color")
                .setColorShape(ColorShape.SQAURE)
                .setColorSwatch(ColorSwatch._500)
                .setDefaultColor("#8062D6")
                .setColorListener { color, colorHex ->
                    binding.rotaryKnob.circleColor = color
                    binding.circleColorView.setBackgroundColor(color)
                }
                .showBottomSheet(supportFragmentManager)
        }

        binding.circleGradientCenterColorView.setOnClickListener {
            MaterialColorPickerDialog
                .Builder(this)
                .setTitle("Choose Color")
                .setColorShape(ColorShape.SQAURE)
                .setColorSwatch(ColorSwatch._500)
                .setDefaultColor("#8062D6")
                .setColorListener { color, colorHex ->
                    binding.rotaryKnob.circleGradientCenterColor = color
                    binding.circleGradientCenterColorView.setBackgroundColor(color)
                }
                .showBottomSheet(supportFragmentManager)
        }

        binding.circleGradientOuterColorView.setOnClickListener {
            MaterialColorPickerDialog
                .Builder(this)
                .setTitle("Choose Color")
                .setColorShape(ColorShape.SQAURE)
                .setColorSwatch(ColorSwatch._500)
                .setDefaultColor("#8062D6")
                .setColorListener { color, colorHex ->
                    binding.rotaryKnob.circleGradientOuterColor = color
                    binding.circleGradientOuterColorView.setBackgroundColor(color)
                }
                .showBottomSheet(supportFragmentManager)
        }

    }

    private fun arrowMethods() {
        isCircleArrowEnable = binding.circleContentLayout.visibility == android.view.View.VISIBLE
        isBorderEnable = binding.borderContentLayout.visibility == android.view.View.VISIBLE


        binding.circleArrow.setOnClickListener {
            isCircleArrowEnable = !isCircleArrowEnable
            binding.circleArrow.setImageResource(if (isCircleArrowEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
            binding.circleContentLayout.visibility =
                if (isCircleArrowEnable) android.view.View.VISIBLE else android.view.View.GONE
        }

        binding.borderArrow.setOnClickListener {
            isBorderEnable = !isBorderEnable
            binding.borderArrow.setImageResource(if (isBorderEnable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_left_24)
            binding.borderContentLayout.visibility =
                if (isBorderEnable) android.view.View.VISIBLE else android.view.View.GONE
        }

    }


}