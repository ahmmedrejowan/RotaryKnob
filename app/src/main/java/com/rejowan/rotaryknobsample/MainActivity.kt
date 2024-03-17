package com.rejowan.rotaryknobsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        binding.differentStylesCardView.setOnClickListener {
            startActivity(Intent(this, DifferentStyles::class.java))
        }

        binding.testVolumeCardView.setOnClickListener {
            startActivity(Intent(this, TestVolume::class.java))
        }

    }


}