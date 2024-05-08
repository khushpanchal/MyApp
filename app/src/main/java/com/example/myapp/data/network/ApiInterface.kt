package com.example.myapp.data.network

import com.example.myapp.data.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "in",
        @Query("page") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 15,
    ): News
}