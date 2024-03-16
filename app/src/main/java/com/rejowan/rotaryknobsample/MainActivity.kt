package com.rejowan.rotaryknobsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.rejowan.rotaryknob.RotaryKnob
import com.rejowan.rotaryknobsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.knobPlaygroundCardView.setOnClickListener {
            startActivity(Intent(this, KnobPlayground::class.java))
        }

        binding.rotaryKnob.knobEnableListener = object : RotaryKnob.OnKnobEnableListener {
            override fun onKnobEnableChanged(isEnable: Boolean, progress: Int) {

            }
        }

        binding.rotaryKnob.progressChangeListener = object : RotaryKnob.OnProgressChangeListener {
            override fun onProgressChanged(progress: Int) {

            }

        }





    }


}