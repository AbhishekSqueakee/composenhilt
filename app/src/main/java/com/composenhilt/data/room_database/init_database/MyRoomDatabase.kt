package com.composenhilt.data.room_database.init_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.composenhilt.data.room_database.rockets.RocketsDao
import com.composenhilt.data.room_database.rockets.RocketsModel
import com.composenhilt.data.room_database.type_converter.ConverterArray
import javax.inject.Inject

@Database(
    entities = [RocketsModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ConverterArray::class,
)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun rocketDao(): RocketsDao

    @Inject
    internal lateinit var myRoomDatabase: MyRoomDatabase

    companion object {
        internal const val DB_NAME = "DB_NAME"
    }

    fun deleteAll() {
        myRoomDatabase.clearAllTables()
    }
}