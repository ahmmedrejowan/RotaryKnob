package com.rejowan.rotaryknobsample.colorpicker.util

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AlertDialog

fun AlertDialog.setButtonTextColor() {
    val positiveTextColor = Color.parseColor("#FF4081")
    getButton(DialogInterface.BUTTON_POSITIVE)?.setTextColor(positiveTextColor)

    val negativeTextColor = Color.parseColor("#FF4081")
    getButton(DialogInterface.BUTTON_NEGATIVE)?.setTextColor(negativeTextColor)
}
