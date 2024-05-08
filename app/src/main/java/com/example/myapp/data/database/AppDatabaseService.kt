package com.example.myapp.data.database

import com.example.myapp.data.model.MainData
import kotlinx.coroutines.flow.Flow

class AppDatabaseService(
    private val mainDatabase: MainDatabase
): DatabaseService {
    override fun getAllData(): Flow<List<MainData>> {
        return mainDatabase.getMainDao().getAllData()
    }

    override fun deleteAllAndInsertAll(articles: List<MainData>) {
        mainDatabase.getMainDao().deleteAllAndInsertAll(articles)
    }
}