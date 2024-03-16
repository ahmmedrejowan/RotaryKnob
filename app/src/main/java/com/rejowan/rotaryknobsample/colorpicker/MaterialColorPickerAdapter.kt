package com.rejowan.rotaryknobsample.colorpicker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.rejowan.rotaryknobsample.R
import com.rejowan.rotaryknobsample.colorpicker.util.ColorUtil
import com.rejowan.rotaryknobsample.databinding.AdapterMaterialColorPickerBinding


class MaterialColorPickerAdapter(private val colors: List<String>) :
    RecyclerView.Adapter<MaterialColorPickerAdapter.MaterialColorViewHolder>() {

    private var isDarkColor = false
    private var color = ""
    private var colorShape = MaterialColorPickerDialog.ColorShape.CIRCLE
    private var isTickColorPerCard = false

    init {
        val darkColors = colors.count { ColorUtil.isDarkColor(it) }
        isDarkColor = (darkColors * 2) >= colors.size
    }

    fun setColorShape(colorShape: MaterialColorPickerDialog.ColorShape) {
        this.colorShape = colorShape
    }

    fun setDefaultColor(color: String) {
        this.color = color
    }

    fun setTickColorPerCard(tickColorPerCard: Boolean) {
        this.isTickColorPerCard = tickColorPerCard
    }

    fun getSelectedColor() = color

    fun getItem(position: Int) = colors[position]

    override fun getItemCount() = colors.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialColorViewHolder {
        val binding =
            AdapterMaterialColorPickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaterialColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaterialColorViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MaterialColorViewHolder(private val binding: AdapterMaterialColorPickerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val newIndex = adapterPosition
                val newColor = getItem(newIndex)

                val oldIndex = colors.indexOf(color)
                color = newColor

                notifyItemChanged(oldIndex)
                notifyItemChanged(newIndex)
            }
        }

        fun bind(position: Int) {
            val colorValue = getItem(position)

            binding.root.tag = position

            binding.colorView.setBackgroundColor(Color.parseColor(colorValue))
            if (colorShape == MaterialColorPickerDialog.ColorShape.SQAURE) {
                binding.colorView.radius =
                    binding.root.context.resources.getDimension(R.dimen.color_card_square_radius)
            }

            val isChecked = color == colorValue
            binding.checkIcon.visibility = if (isChecked) View.VISIBLE else View.GONE

            val textColor = if (isTickColorPerCard && ColorUtil.isDarkColor(colorValue)) Color.WHITE else Color.BLACK
            binding.checkIcon.setColorFilter(textColor)
        }
    }
}
