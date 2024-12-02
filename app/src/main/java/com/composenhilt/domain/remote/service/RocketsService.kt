package com.composenhilt.domain.remote.service

import com.composenhilt.data.room_database.rockets.RocketsModel
import retrofit2.http.GET

interface RocketsService {

    @GET("v4/rockets")
    suspend fun getRockets(): MutableList<RocketsModel>
}