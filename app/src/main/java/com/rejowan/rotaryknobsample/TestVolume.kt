package com.rejowan.rotaryknobsample

import android.content.Context
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rejowan.rotaryknob.RotaryKnob
import com.rejowan.rotaryknobsample.databinding.ActivityTestVolumeBinding

class TestVolume : AppCompatActivity() {

    private val binding by lazy { ActivityTestVolumeBinding.inflate(layoutInflater) }

    private val audioManager: AudioManager by lazy {
        getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val minVolume = 0
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        binding.rotaryKnob.min = minVolume
        binding.rotaryKnob.max = maxVolume

        binding.rotaryKnob.currentProgress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)


        binding.rotaryKnob.knobEnableListener = object : RotaryKnob.OnKnobEnableListener {
            override fun onKnobEnableChanged(isEnable: Boolean, progress: Int) {
                if (isEnable) {
                    audioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_UNMUTE,
                        0
                    )
                } else {
                    audioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_MUTE,
                        0
                    )
                }
            }
        }

        binding.rotaryKnob.progressChangeListener = object : RotaryKnob.OnProgressChangeListener {
            override fun onProgressChanged(progress: Int) {
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    progress,
                    0
                )
            }

        }


    }



}