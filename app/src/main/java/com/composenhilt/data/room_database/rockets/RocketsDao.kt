package com.composenhilt.data.room_database.rockets

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.composenhilt.data.room_database.init_database.BaseDao

@Dao
interface RocketsDao : BaseDao<RocketsModel, MutableList<RocketsModel>> {

    @Query("SELECT * FROM rocketsmodel")
    suspend fun getAllRockets(): MutableList<RocketsModel>

    @Query("SELECT * FROM rocketsmodel WHERE id=:id")
    suspend fun getRocketById(id: String): RocketsModel?
}