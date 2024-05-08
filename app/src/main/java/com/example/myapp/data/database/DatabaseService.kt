package com.example.myapp.data.database

import com.example.myapp.data.model.MainData
import kotlinx.coroutines.flow.Flow

interface DatabaseService {
    fun getAllData(): Flow<List<MainData>>
    fun deleteAllAndInsertAll(articles: List<MainData>)
}