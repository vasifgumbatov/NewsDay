package com.vasifgumbatov.news.Api

import com.vasifgumbatov.news.Response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String
    ): Response<WeatherResponse>
}