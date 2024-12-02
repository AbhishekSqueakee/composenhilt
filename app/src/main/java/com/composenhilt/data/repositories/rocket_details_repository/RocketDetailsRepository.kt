package com.composenhilt.data.repositories.rocket_details_repository

import com.composenhilt.data.room_database.init_database.MyRoomDatabase
import com.composenhilt.data.room_database.rockets.RocketsModel
import javax.inject.Inject

class RocketDetailsRepository  @Inject constructor(
    var myRoomDatabase: MyRoomDatabase
) {

    suspend fun queryRocketById(id: String): RocketsModel? {
        return myRoomDatabase.rocketDao().getRocketById(id)
    }
}