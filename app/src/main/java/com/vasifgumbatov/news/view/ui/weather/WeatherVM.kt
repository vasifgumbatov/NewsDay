package com.vasifgumbatov.news.view.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vasifgumbatov.news.data.remote.response.ApiState
import com.vasifgumbatov.news.data.remote.response.ErrorModel
import com.vasifgumbatov.news.data.remote.api.WeatherDataSource
import com.vasifgumbatov.news.data.remote.response.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherVM @Inject constructor(
    private val weatherApiService: WeatherDataSource
) : ViewModel() {
    fun getWeatherData(): LiveData<WeatherResponse> = weatherData
    private val weatherData = MutableLiveData<WeatherResponse>()

    fun getWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            val result = apiCall { weatherApiService.getCurrentWeather(city, apiKey) }
            when (result) {
                is ApiState.Success<*> -> {
                    val data = result.data as? WeatherResponse
                    data?.let {
                        weatherData.postValue(it)
                    }
                }

                is ApiState.Error -> {
                }
            }
        }
    }

    private suspend fun <T> apiCall(call: suspend () -> Response<T>): ApiState<T> {
        val result = call.invoke()
        return try {
            if (result.isSuccessful && result.body() != null) {
                ApiState.Success(result.body()!!)
            } else {
                val gson = Gson()
                val jsonObject = JSONObject(result.errorBody()?.charStream()?.readText() ?: "{}")
                val error = gson.fromJson(jsonObject.toString(), ErrorModel::class.java)
                ApiState.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ApiState.Error(
                ErrorModel(errorCode = 505, errorTitle = "Xeta", errorDescription = "Sistem xetasi")
            )
        }
    }
}