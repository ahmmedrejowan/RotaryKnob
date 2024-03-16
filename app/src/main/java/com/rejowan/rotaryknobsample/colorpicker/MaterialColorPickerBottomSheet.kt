package com.rejowan.rotaryknobsample.colorpicker

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rejowan.rotaryknobsample.colorpicker.MaterialColorPickerDialog.ColorListener
import com.rejowan.rotaryknobsample.colorpicker.MaterialColorPickerDialog.ColorShape
import com.rejowan.rotaryknobsample.colorpicker.MaterialColorPickerDialog.ColorSwatch
import com.rejowan.rotaryknobsample.colorpicker.MaterialColorPickerDialog.DismissListener
import com.rejowan.rotaryknobsample.colorpicker.util.ColorUtil
import com.rejowan.rotaryknobsample.databinding.DialogBottomsheetMaterialColorPickerBinding


class MaterialColorPickerBottomSheet : BottomSheetDialogFragment() {

    private var title: String? = null
    private var positiveButton: String? = null
    private var negativeButton: String? = null
    private var colorListener: ColorListener? = null
    private var dismissListener: DismissListener? = null
    private var defaultColor: String? = null
    private var colorShape: ColorShape = ColorShape.CIRCLE
    private var colorSwatch: ColorSwatch = ColorSwatch._300
    private var colors: List<String>? = null
    private var isTickColorPerCard: Boolean = false

    companion object {

        private const val EXTRA_TITLE = "extra.title"
        private const val EXTRA_POSITIVE_BUTTON = "extra.positive_Button"
        private const val EXTRA_NEGATIVE_BUTTON = "extra.negative_button"

        private const val EXTRA_DEFAULT_COLOR = "extra.default_color"
        private const val EXTRA_COLOR_SHAPE = "extra.color_shape"
        private const val EXTRA_COLOR_SWATCH = "extra.color_swatch"
        private const val EXTRA_COLORS = "extra.colors"
        private const val EXTRA_IS_TICK_COLOR_PER_CARD = "extra.is_tick_color_per_card"

        fun getInstance(dialog: MaterialColorPickerDialog): MaterialColorPickerBottomSheet {
            val bundle = Bundle().apply {
                putString(EXTRA_TITLE, dialog.title)
                putString(EXTRA_POSITIVE_BUTTON, dialog.positiveButton)
                putString(EXTRA_NEGATIVE_BUTTON, dialog.negativeButton)

                putString(EXTRA_DEFAULT_COLOR, dialog.defaultColor)
                putInt(EXTRA_COLOR_SWATCH, dialog.colorSwatch.ordinal)
                putInt(EXTRA_COLOR_SHAPE, dialog.colorShape.ordinal)
                putBoolean(EXTRA_IS_TICK_COLOR_PER_CARD, dialog.isTickColorPerCard)

                var list: ArrayList<String>? = null
                if (dialog.colors != null) {
                    list = ArrayList(dialog.colors)
                }
                putStringArrayList(EXTRA_COLORS, list)
            }

            return MaterialColorPickerBottomSheet().apply {
                arguments = bundle
            }
        }
    }

    fun setColorListener(listener: ColorListener?): MaterialColorPickerBottomSheet {
        this.colorListener = listener
        return this
    }

    fun setDismissListener(listener: DismissListener?): MaterialColorPickerBottomSheet {
        this.dismissListener = listener
        return this
    }

    private val binding by lazy { DialogBottomsheetMaterialColorPickerBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            title = it.getString(EXTRA_TITLE)
            positiveButton = it.getString(EXTRA_POSITIVE_BUTTON)
            negativeButton = it.getString(EXTRA_NEGATIVE_BUTTON)

            defaultColor = it.getString(EXTRA_DEFAULT_COLOR)
            colorSwatch = ColorSwatch.values()[it.getInt(EXTRA_COLOR_SWATCH)]
            colorShape = ColorShape.values()[it.getInt(EXTRA_COLOR_SHAPE)]

            colors = it.getStringArrayList(EXTRA_COLORS)
            isTickColorPerCard = it.getBoolean(EXTRA_IS_TICK_COLOR_PER_CARD)
        }

        title?.let { binding.titleTxt.text = it }
        positiveButton?.let { binding.positiveBtn.text = it }
        negativeButton?.let { binding.negativeBtn.text = it }

        val colorList = colors ?: ColorUtil.getColors(requireContext(), colorSwatch.value)
        val adapter = MaterialColorPickerAdapter(colorList)
        adapter.setColorShape(colorShape)
        adapter.setTickColorPerCard(isTickColorPerCard)
        if (!defaultColor.isNullOrBlank()) {
            adapter.setDefaultColor(defaultColor!!)
        }

        binding.materialColorRV.setHasFixedSize(true)
        binding.materialColorRV.layoutManager = FlexboxLayoutManager(context)
        binding.materialColorRV.adapter = adapter

        binding.positiveBtn.setOnClickListener {
            val color = adapter.getSelectedColor()
            if (color.isNotBlank()) {
                colorListener?.onColorSelected(ColorUtil.parseColor(color), color)
            }
            dismiss()
        }
        binding.negativeBtn.setOnClickListener { dismiss() }
    }

    override fun dismiss() {
        super.dismiss()
        dismissListener?.onDismiss()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismissListener?.onDismiss()
    }
}
