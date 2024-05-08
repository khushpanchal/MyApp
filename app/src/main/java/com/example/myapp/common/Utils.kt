package com.example.myapp.common

import android.content.Context

object Utils {

    fun readJsonFromAssets(context: Context, pageNum: Int): String {
        val fileName = when(pageNum) {
            1 -> "sample.json"
            2 -> "sample2.json"
            3 -> "sample3.json"
            else -> "sample4.json"
        }
        return context.applicationContext.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }
//    fun readJsonFromAssets(context: Context): String {
//        return context.applicationContext.assets.open("sample.json").bufferedReader().use {
//            it.readText()
//        }
//    }
}