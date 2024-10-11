package com.vasifgumbatov.news

import com.vasifgumbatov.news.Api.NewsApiService
import com.vasifgumbatov.news.Response.NewsResponse

class GetNewsUseCase(private val newsApiService: NewsApiService) {

    suspend fun execute(sources: String, apiKey: String): NewsResponse? {
        return try {
            val response = newsApiService.getTopHeadlines(sources, apiKey)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}