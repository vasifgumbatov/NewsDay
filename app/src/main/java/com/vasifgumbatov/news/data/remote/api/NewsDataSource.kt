package com.vasifgumbatov.news.data.remote.api

import  com.vasifgumbatov.news.data.remote.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDataSource {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources: String,
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getEverythingTech(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources: String,
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun getEverythingBTC(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun getEverythingApple(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("sortBy") sortBy: String
    ): Response<NewsResponse>

}