package com.vasifgumbatov.news.data.remote.interactor

import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import com.vasifgumbatov.news.data.remote.response.NewsResponse
import javax.inject.Inject
import javax.inject.Singleton

class GetNewsUseCase @Inject constructor(private val newsApiService: NewsDataSource) {
    suspend fun executeMainNews(apiKey: String, sources: String): NewsResponse? {
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

    suspend fun executeBTCNews(apiKey: String, q: String, pageSize: Int, page: Int): NewsResponse? {
        return try {
            val response = newsApiService.getEverythingBTC(q, apiKey, pageSize, page)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun executeTechNews(apiKey: String, sources: String): NewsResponse? {
        return try {
            val response = newsApiService.getEverythingTech(sources, apiKey)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun executeAppleNews(q: String, apiKey: String, from: String, to: String, sortBy: String): NewsResponse? {
        return try {
            val response = newsApiService.getEverythingApple(q, apiKey, from, to, sortBy)
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