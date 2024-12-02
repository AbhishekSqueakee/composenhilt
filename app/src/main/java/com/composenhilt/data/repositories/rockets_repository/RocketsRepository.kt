package com.composenhilt.data.repositories.rockets_repository

import com.composenhilt.data.room_database.init_database.MyRoomDatabase
import com.composenhilt.data.room_database.rockets.RocketsModel
import com.composenhilt.domain.remote.service.RocketsService
import javax.inject.Inject

class RocketsRepository @Inject constructor(
    private var rocketsService: RocketsService,
    private var myRoomDatabase: MyRoomDatabase
) {
    suspend fun fetchRocketsData(): MutableList<RocketsModel> {
        val rocketsList = rocketsService.getRockets()
        return rocketsList
    }

    suspend fun queryToGetAllRockets(): MutableList<RocketsModel> {
        return myRoomDatabase.rocketDao().getAllRockets()
    }
}