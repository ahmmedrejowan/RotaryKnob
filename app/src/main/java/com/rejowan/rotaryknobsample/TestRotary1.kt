package com.rejowan.rotaryknobsample

import android.content.Context
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rejowan.rotaryknob.RotaryKnob
import com.rejowan.rotaryknobsample.databinding.ActivityTestRotary1Binding
import com.rejowan.rotaryknobsample.databinding.ActivityTestVolumeBinding

class TestRotary1 : AppCompatActivity() {

    private val binding by lazy { ActivityTestRotary1Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
