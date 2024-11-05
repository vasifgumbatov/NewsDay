package com.vasifgumbatov.news.view.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.data.local.repository.FavoriteNewsRepository
import com.vasifgumbatov.news.data.remote.api.ApiManager
import com.vasifgumbatov.news.data.remote.api.NewsApiService
import com.vasifgumbatov.news.data.remote.interactor.GetNewsUseCase
import com.vasifgumbatov.news.data.remote.response.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsApiService: NewsApiService,
    private val repository: FavoriteNewsRepository
) : ViewModel() {
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

    fun addToDB(article: Article) {
        viewModelScope.launch {
            repository.insertFavorite(
                FavoriteEntity(
                    id = 0,
                    title = article.title,
                    description = article.description!!,
                    isLiked = true,
                    urlToImage = article.urlToImage!!
                )
            )
        }
    }
}
