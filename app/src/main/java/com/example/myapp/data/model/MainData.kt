package com.example.myapp.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class News(
    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val totalResults: Int,

    @SerializedName("articles")
    val articles: List<MainData>
)

@Entity(
    tableName = "cached_data",
    indices = [Index(
        value = ["title"],
        unique = true
    )]
)
data class MainData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @SerializedName("title")
    val title: String,

    @SerializedName("urlToImage")
    val urlToImage: String?
) : Serializable