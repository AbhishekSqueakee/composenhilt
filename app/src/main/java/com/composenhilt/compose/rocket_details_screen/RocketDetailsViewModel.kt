package com.composenhilt.compose.rocket_details_screen

import android.app.Application
import android.util.Log
import com.composenhilt.data.room_database.rockets.RocketsModel
import com.composenhilt.utils.base_classes.BaseViewModel
import com.composenhilt.data.repositories.rocket_details_repository.RocketDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketDetailsViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {

    private val _rocketDetails = MutableStateFlow(RocketsModel())
    val rocketDetails: StateFlow<RocketsModel> = _rocketDetails

    @Inject
    internal lateinit var rocketDetailsRepository: RocketDetailsRepository

    fun queryRocketById(id: String) = launch {
        flow {
            val rocketsModel: RocketsModel? = rocketDetailsRepository.queryRocketById(id)
            emit(rocketsModel)
        }.flowOn(Dispatchers.Default)
            .catch { e ->
                error.value = handleErrorMessage(e)
            }.collect {
                if (it != null) {
                    _rocketDetails.value = it
                }
            }
    }
}