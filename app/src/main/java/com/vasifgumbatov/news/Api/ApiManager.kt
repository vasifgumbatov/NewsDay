package com.vasifgumbatov.news.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var retrofit2: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun newsApiService(): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    fun weatherApiService(): WeatherApiService {
        return retrofit2.create(WeatherApiService::class.java)
    }
}