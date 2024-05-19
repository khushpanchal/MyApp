package com.example.myapp.data.repository

import com.example.myapp.data.database.DatabaseService
import com.example.myapp.data.model.MainData
import com.example.myapp.data.network.FakeNetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val fakeNetworkService: FakeNetworkService, // private val apiInterface: ApiInterface,
    private val databaseService: DatabaseService
) {

    suspend fun getMainData(): Flow<List<MainData>> {
        return flow {
            fetchAndSaveToDb()
            emit(
                databaseService.getAllData().first() //List<MainData>
            )
        }
    }

    private suspend fun fetchAndSaveToDb() {
        try {
            val mainData = fakeNetworkService.getMainData().articles
            databaseService.deleteAllAndInsertAll(mainData)
        } catch (ignore: Exception) {

        }
    }

    suspend fun getDataFromDb(): Flow<List<MainData>> {
        return flow {
            emit(
                databaseService.getAllData().first() //List<MainData>
            )
        }
    }

}