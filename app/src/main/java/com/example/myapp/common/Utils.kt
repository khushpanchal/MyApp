package com.example.myapp.common

import android.content.Context

object Utils {

    fun readJsonFromAssets(context: Context): String {
        return context.applicationContext.assets.open("sample.json").bufferedReader().use {
            it.readText()
        }
    }
}