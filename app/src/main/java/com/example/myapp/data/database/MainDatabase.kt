package com.example.myapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapp.data.model.MainData

@Database(entities = [MainData::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {

    abstract fun getMainDao(): MainDao

}