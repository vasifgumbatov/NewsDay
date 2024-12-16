package com.vasifgumbatov.news.data.remote.api

import  com.vasifgumbatov.news.data.remote.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDataSource {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources: String
    ): Response<NewsResponse>
}