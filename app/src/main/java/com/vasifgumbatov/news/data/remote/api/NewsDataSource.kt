package com.vasifgumbatov.news.data.remote.api

import  com.vasifgumbatov.news.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDataSource {

    // Main news
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources: String,
    ): Response<NewsResponse>

    // Tech news
    @GET("top-headlines")
    suspend fun getEverythingTech(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources: String,
    ): Response<NewsResponse>

    // USA news
    @GET("top-headlines")
    suspend fun getTopHeadlineBusiness(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
    ): Response<NewsResponse>

    // BTC news
    @GET("everything")
    suspend fun getEverythingBTC(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Response<NewsResponse>

}