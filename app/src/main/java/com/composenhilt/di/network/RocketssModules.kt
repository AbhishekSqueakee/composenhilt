package com.composenhilt.di.network

import com.composenhilt.domain.remote.service.RocketsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RocketssModules {
    private const val BASE_URL = "https://api.spacexdata.com"

    @Provides
    @Singleton
    fun getRocketsList(retrofit: Retrofit): RocketsService {
        return retrofit.create(RocketsService::class.java)
    }

    @Provides
    @Singleton
    fun requestBuilder(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}