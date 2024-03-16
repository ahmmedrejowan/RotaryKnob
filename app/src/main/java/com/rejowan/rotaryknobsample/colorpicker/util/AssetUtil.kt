package com.rejowan.rotaryknobsample.colorpicker.util

import android.content.Context

object AssetUtil {

    fun readAssetFile(context: Context, fileName: String): String {
        return context.assets.open(fileName)
            .bufferedReader().use {
                it.readText()
            }
    }
}
