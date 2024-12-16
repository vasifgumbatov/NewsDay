package com.vasifgumbatov.news.data.remote.interactor

import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import com.vasifgumbatov.news.data.remote.response.NewsResponse

class GetNewsUseCase(private val newsApiService: NewsDataSource) {
    suspend fun execute(apiKey: String, sources: String): NewsResponse? {
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