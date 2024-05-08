package com.example.myapp.data.repository

import com.example.myapp.data.database.DatabaseService
import com.example.myapp.data.model.MainData
import com.example.myapp.data.network.FakeNetworkService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val fakeNetworkService: FakeNetworkService,
    private val databaseService: DatabaseService
) {

    suspend fun getMainData(pageNum: Int): List<MainData> {
        if (pageNum == 1) {
            fetchAndSaveToDb(pageNum)
            return databaseService.getAllData().first()
        }
        return fakeNetworkService.getMainData(pageNum).articles
    }
//    suspend fun getMainData(): Flow<List<MainData>> {
//        return flow {
//            fetchAndSaveToDb()
//            emit(
//                databaseService.getAllData().first() //List<MainData>
//            )
//        }
//    }


    private suspend fun fetchAndSaveToDb(pageNum: Int) {
        try {
            val mainData = fakeNetworkService.getMainData(pageNum).articles
            databaseService.deleteAllAndInsertAll(mainData)
        } catch (ignore: Exception) {

        }
    }
//    private suspend fun fetchAndSaveToDb() {
//        try {
//            val mainData = fakeNetworkService.getMainData().articles
//            databaseService.deleteAllAndInsertAll(mainData)
//        } catch (ignore: Exception) {
//
//        }
//    }


    suspend fun getDataFromDb(): List<MainData> {
        return databaseService.getAllData().first()
    }
//    suspend fun getDataFromDb(): Flow<List<MainData> > {
//        return flow {
//            emit(
//                databaseService.getAllData().first() //List<MainData>
//            )
//        }
//      }

}