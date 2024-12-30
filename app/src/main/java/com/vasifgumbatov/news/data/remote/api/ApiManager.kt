package com.vasifgumbatov.news.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Bu object-i sile bilersen
object ApiManager {

    private var okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor()).apply {
            HttpLoggingInterceptor.Level.BODY
        }
        .build()

    //news
    private var newsRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    //weather
    private var weatherRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun newsApiService(): NewsDataSource {
        return newsRetrofit.create(NewsDataSource::class.java)
    }

    fun weatherApiService(): WeatherDataSource {
        return weatherRetrofit.create(WeatherDataSource::class.java)
    }
}