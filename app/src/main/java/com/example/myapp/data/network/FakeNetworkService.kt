package com.example.myapp.data.network

import android.content.Context
import com.example.myapp.common.Utils
import com.example.myapp.data.model.News
import com.google.gson.Gson

class FakeNetworkService(private val context: Context){

    fun getMainData(pageNum: Int): News {
        val jsonString = Utils.readJsonFromAssets(context, pageNum)
        return Gson().fromJson(jsonString, News::class.java)
    }
//    fun getMainData(): News {
//        val jsonString = Utils.readJsonFromAssets(context)
//        return Gson().fromJson(jsonString, News::class.java)
//    }

}