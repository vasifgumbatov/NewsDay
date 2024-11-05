package com.vasifgumbatov.news.data.remote.interactor

import com.vasifgumbatov.news.data.remote.api.NewsApiService
import com.vasifgumbatov.news.data.remote.response.NewsResponse

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