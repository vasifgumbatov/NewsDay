package com.vasifgumbatov.news.ui.home.applenews

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
class AppleNewsVM @Inject constructor(
    private val repository: FavoriteNewsRepository,
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    val newsLiveData = MutableLiveData<List<Article>>()
    val errorLiveData = MutableLiveData<String>()
    private var favoriteNew = listOf<FavoriteEntity>()

    init {
        getFavoriteNews {
            Log.d("FavoriteNews", "Favorite news: $favoriteNew")
        }
    }

    fun fetchNewsApple(q: String, apiKey: String, from: String, to: String, sortBy: String) {
        viewModelScope.launch {

            val newsResponse = getNewsUseCase.executeAppleNews(
                q, apiKey, from, to, sortBy
            )

            if (newsResponse != null) {
                val updatedArticles = newsResponse.articles.map { article ->
                    article.copy(isLiked = favoriteNew.any { it.title == article.title })
                }
                newsLiveData.postValue(updatedArticles)
            } else {
                errorLiveData.postValue("Failed to load news")
            }
        }
    }

    private fun getFavoriteNews(callback: () -> Unit) {
        viewModelScope.launch {
            favoriteNew = repository.getLikedNews()
            callback()
        }
    }

    fun addAppleNewsToDB(article: Article) {
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