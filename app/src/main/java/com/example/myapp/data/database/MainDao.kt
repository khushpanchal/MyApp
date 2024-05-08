package com.example.myapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapp.data.model.MainData
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<MainData>)

    @Query("SELECT * FROM cached_data")
    fun getAllData(): Flow<List<MainData>>

    @Query("DELETE FROM cached_data")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(articles: List<MainData>) {
        deleteAll()
        return insertAll(articles)
    }

}