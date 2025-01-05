package com.vasifgumbatov.news.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.data.local.repository.FavoriteNewsRepository
import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import com.vasifgumbatov.news.data.remote.interactor.GetNewsUseCase
import com.vasifgumbatov.news.data.remote.response.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeNewsVM @Inject constructor(
    private val newsApiService: NewsDataSource,
    private val repository: FavoriteNewsRepository,
) : ViewModel() {

    init {
        getFavoriteNews()
    }

    val newsLiveData = MutableLiveData<List<Article>>()
    val errorLiveData = MutableLiveData<String>()
    private var favoriteNew = listOf<FavoriteEntity>()

    fun fetchNews(sources: String, apiKey: String) {
        viewModelScope.launch {
            val newsResponse = GetNewsUseCase(newsApiService).executeMainNews(
                sources, apiKey,
            )
            if (newsResponse != null) {
                newsResponse.articles.forEach { article ->
                    article.isLiked = favoriteNew.any { it.title == article.title }
                }
                newsLiveData.postValue(newsResponse.articles)
            } else {
                errorLiveData.postValue("Failed to load news")
            }
        }
    }

    private fun getFavoriteNews() {
        viewModelScope.launch {
            favoriteNew = repository.getLikedNews()
        }
    }

    fun addMainNewsToDB(article: Article) {
        viewModelScope.launch {
            val favoriteEntity = FavoriteEntity(
                id = 0,
                author = article.author ?: "Unknown",  // Default value for null
                title = article.title ?: "No Title",
                description = article.description ?: "No Description",
                url = article.url ?: "No URL",
                content = article.content ?: "No Content",
                publishedAt = article.publishedAt ?: "Unknown Date",
                isLiked = true,
                urlToImage = article.urlToImage ?: "No Image"
            )
            try {
                repository.insertFavorite(favoriteEntity)
            } catch (e: Exception) {
                Log.e("AddToDB", "Error adding to database: ${e.message}")
            }
        }
    }
}
