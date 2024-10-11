package com.vasifgumbatov.news.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasifgumbatov.news.Api.ApiManager
import com.vasifgumbatov.news.Api.NewsApiService
import com.vasifgumbatov.news.GetNewsUseCase
import com.vasifgumbatov.news.Response.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsApiService: NewsApiService) : ViewModel() {
    val newsLiveData = MutableLiveData<List<Article>>()
    val errorLiveData = MutableLiveData<String>()

    fun fetchNews(sources: String, apiKey: String) {
        viewModelScope.launch {
            val newsResponse = GetNewsUseCase(newsApiService).execute(sources, apiKey)
            if (newsResponse != null) {
                newsLiveData.postValue(newsResponse.articles)
            } else {
                errorLiveData.postValue("Failed to load news")
            }
        }

        fun fetchTopHeadlines() {
            viewModelScope.launch {
                val response = ApiManager.newsApiService()
                    .getTopHeadlines("bbc-news", "331cc6318d5f4e4bbdddfe9f3d4d6a93")
                if (response.isSuccessful) {
                    newsLiveData.postValue(response.body()?.articles ?: emptyList())
                }
            }
        }
    }
}
