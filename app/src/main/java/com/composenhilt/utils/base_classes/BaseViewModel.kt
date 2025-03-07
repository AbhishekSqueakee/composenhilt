package com.composenhilt.utils.base_classes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.composenhilt.R
import com.composenhilt.compose.application.ComposeAndHilt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job()
    internal val loading = MutableLiveData<Boolean>()
    internal val error = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    protected fun handleErrorMessage(e: Throwable): String {
        return when (e) {
            is HttpException -> {
                val responseBody: ResponseBody? = e.response()?.errorBody()
                val errorMessage = responseBody?.let { getErrorMessage(it.string()) }
                    ?: getApplication<ComposeAndHilt>().getString(R.string.something_went_wrong)
                errorMessage
            }
            is SocketTimeoutException -> {
                getApplication<ComposeAndHilt>().getString(R.string.something_went_wrong_with_server)
            }
            is IOException -> {
                getApplication<ComposeAndHilt>().getString(R.string.check_your_internet_connection)
            }
            else -> {
                e.message ?: getApplication<ComposeAndHilt>().getString(R.string.something_went_wrong)
            }
        }
    }

    private fun getErrorMessage(handleErrorMessage: String): String? {
        return try {
            val jsonObject = JSONObject(handleErrorMessage)
            val jsonArray = JSONArray(handleErrorMessage)
            jsonObject.optString("message")//depend from server the json key - this is an example
        } catch (e: Exception) {
            e.message
            getApplication<ComposeAndHilt>().getString(R.string.something_went_wrong)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}