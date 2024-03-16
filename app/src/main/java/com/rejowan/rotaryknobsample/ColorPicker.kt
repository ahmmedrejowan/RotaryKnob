package com.rejowan.rotaryknobsample

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rejowan.rotaryknobsample.databinding.ColorPickerDialogBinding
import com.rejowan.rotaryknobsample.databinding.ItemSingleColorBinding

class ColorPicker : BottomSheetDialogFragment() {

    private val binding by lazy {
        ColorPickerDialogBinding.inflate(layoutInflater)
    }

    private val colorList = arrayListOf<String>().apply {
        // Default
        add("#8062D6")

        // Red
        add("#EF5350")
        add("#D32F2F")

        // Deep Purple
        add("#7E57C2")
        add("#512DA8")

        // Light Blue
        add("#29B6F6")
        add("#0288D1")

        // Green
        add("#66BB6A")
        add("#388E3C")

        // Yellow
        add("#FFEE58")
        add("#FBC02D")

        // Deep Orange
        add("#FF7043")
        add("#E64A19")

        // Blue Grey
        add("#78909C")
        add("#455A64")

        // Pink
        add("#EC407A")
        add("#C2185B")

        // Indigo
        add("#5C6BC0")
        add("#303F9F")

        // Cyan
        add("#26C6DA")
        add("#0097A7")

        // Light Green
        add("#9CCC65")
        add("#689F38")

        // Amber
        add("#FFCA28")
        add("#FFA000")

        // Brown
        add("#8D6E63")
        add("#5D4037")

        // Purple
        add("#AB47BC")
        add("#7B1FA2")

        // Blue
        add("#42A5F5")
        add("#1976D2")

        // Teal
        add("#26A69A")
        add("#00796B")

        // Lime
        add("#D4E157")
        add("#AFB42B")

        // Orange
        add("#FFA726")
        add("#F57C00")

        // Grey
        add("#BDBDBD")
        add("#757575")

        // Black
        add("#000000")
        add("#222222")

        // White
        add("#FFFFFF")
    }

    private lateinit var listener: OnColorSelectedListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ColorAdapter(colorList, listener)
        binding.colorRV.setHasFixedSize(true)
        binding.colorRV.adapter = adapter

        adapter.notifyDataSetChanged()


    }

    fun setOnColorSelectedListener(listener: OnColorSelectedListener){
        this.listener = listener
    }


  inner class ColorAdapter (private val listOfColors: MutableList<String>, private val listener: OnColorSelectedListener ) : RecyclerView.Adapter<ColorAdapter.ViewHolder>(){

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val binding = ItemSingleColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = listOfColors[position]
            holder.binding.colorView.setCardBackgroundColor(Color.parseColor(item))
            holder.binding.colorView.radius = 20f

            holder.binding.colorView.setOnClickListener {
                listener.onColorSelected(item, hexToColorInt(item))
                dismiss()
            }

        }

        override fun getItemCount(): Int {
            return listOfColors.size
        }

      inner class ViewHolder (val binding: ItemSingleColorBinding) : RecyclerView.ViewHolder(binding.root)
    }

    interface OnColorSelectedListener {
        fun onColorSelected(colorString: String, colorInt: Int)
    }


    companion object {
        fun colorIntToHex(color: Int): String {
            return String.format("#%06X", 0xFFFFFF and color)
        }

        fun hexToColorInt(hex: String): Int {
            return Color.parseColor(hex)
        }
    }



}