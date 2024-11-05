package com.vasifgumbatov.news.view.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vasifgumbatov.news.data.remote.response.ApiResult
import com.vasifgumbatov.news.data.remote.response.ErrorModel
import com.vasifgumbatov.news.data.remote.api.WeatherApiService
import com.vasifgumbatov.news.data.remote.response.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherApiService: WeatherApiService
) : ViewModel() {
    fun getWeatherData(): LiveData<WeatherResponse> = weatherData
    private val weatherData = MutableLiveData<WeatherResponse>()

    fun getWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            val result = apiCall { weatherApiService.getCurrentWeather(city, apiKey) }
            when (result) {
                is ApiResult.Success<*> -> {
                    val data = result.data as? WeatherResponse
                    data?.let {
                        weatherData.postValue(it)
                    }
                }

                is ApiResult.Error -> {
                }
            }
        }
    }

    suspend fun <T> apiCall(call: suspend () -> Response<T>): ApiResult<T> {
        val result = call.invoke()
        return try {
            if (result.isSuccessful && result.body() != null) {
                ApiResult.Success(result.body()!!)
            } else {
                val gson = Gson()
                val jsonObject = JSONObject(result.errorBody()?.charStream()?.readText() ?: "{}")
                val error = gson.fromJson(jsonObject.toString(), ErrorModel::class.java)
                ApiResult.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResult.Error(
                ErrorModel(errorCode = 505, errorTitle = "Xeta", errorDescription = "Sistem xetasi")
            )
        }
    }
}