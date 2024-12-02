package com.composenhilt.compose.rockets_screen

import android.app.Application
import android.util.Log
import com.composenhilt.data.room_database.rockets.RocketsModel
import com.composenhilt.utils.base_classes.BaseViewModel
import com.composenhilt.data.repositories.rockets_repository.RocketsRepository
import com.composenhilt.data.room_database.init_database.MyRoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    application: Application,
    private var myRoomDatabase: MyRoomDatabase) : BaseViewModel(application) {

    private val _rocketsModelStateFlow =
        MutableStateFlow<MutableList<RocketsModel>>(mutableListOf())
    val rocketsModelStateFlow: StateFlow<MutableList<RocketsModel>> = _rocketsModelStateFlow

    init {
        requestForRocketData()
        offline()
    }

    @Inject
    internal lateinit var rocketsRepository: RocketsRepository

    private fun requestForRocketData() = launch {
        loading.value = true
        flow {
            val rocketsList =
                rocketsRepository.fetchRocketsData() //get the data from the server
            if (rocketsList.isNotEmpty()) {
                saveRocketDataIntoDatabase(rocketsList)
            }
            emit(rocketsList)
        }.flowOn(Dispatchers.IO)
            .catch { e ->
                loading.value = false
                error.value = handleErrorMessage(e)
            }.collect {
                loading.value = false
                _rocketsModelStateFlow.value = it
            }
    }

    private fun offline() = launch {
        loading.value = true
        flow {
            val rocketsList =
                rocketsRepository.queryToGetAllRockets() //get the data from the local database
            emit(rocketsList)
        }.flowOn(Dispatchers.IO)
            .catch { e ->
                loading.value = false
                error.value = handleErrorMessage(e)
            }.collect {
                loading.value = false
                _rocketsModelStateFlow.value = it
            }
    }

    private suspend fun saveRocketDataIntoDatabase(rocketsModelList: MutableList<RocketsModel>) {
        insertTheRockets(rocketsModelList, myRoomDatabase).collect()
    }

    private fun insertTheRockets(
        rocketsModelList: MutableList<RocketsModel>,
        myRoomDatabase: MyRoomDatabase
    ) =
        flow {
            try {

                saveRockets(rocketsModelList).collect {
                    myRoomDatabase.rocketDao()
                        .insertOrReplaceList(rocketsModelList)
                }
                emit(
                    myRoomDatabase.rocketDao().getAllRockets()
                )

            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
            }
        }

    private fun saveRockets(
        rocketsModelList: MutableList<RocketsModel>
    ) =
        flow {
            val rocketsModelListSaved = mutableListOf<RocketsModel>()
            rocketsModelList.forEach { rocket ->
                rocketsModelListSaved.add(rocket)
            }
            emit(rocketsModelListSaved)
        }
}