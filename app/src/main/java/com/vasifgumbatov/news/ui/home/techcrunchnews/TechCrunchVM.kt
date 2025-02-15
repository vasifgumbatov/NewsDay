package com.vasifgumbatov.news.ui.home.techcrunchnews

import android.util.Log
import androidx.lifecycle.LiveData
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
class TechCrunchVM @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val repository: FavoriteNewsRepository,
) : ViewModel() {

    val newsLiveData = MutableLiveData<List<Article>>()
    val errorLiveData = MutableLiveData<String>()
    private var favoriteNew = listOf<FavoriteEntity>()

    private val _favoriteRemovedLiveData = MutableLiveData<String>()
    val favoriteRemovedLiveData: LiveData<String> = _favoriteRemovedLiveData

    // Initialize the favorite news list
    init {
        getFavoriteNews()
    }

    // Function to fetch news from the API
    fun fetchTechNews(sources: String, apiKey: String) {
        viewModelScope.launch {
            val newsResponse = getNewsUseCase.executeTechNews(
                sources, apiKey
            )
            if (newsResponse != null) {
                newsResponse.articles.forEach { article ->
                    article.isLiked = favoriteNew.any { it.title == article.title }
                }
                newsLiveData.postValue(newsResponse.articles)
            } else {
                errorLiveData.postValue("Failed to load news!")
            }
        }
    }

    private fun getFavoriteNews() {
        viewModelScope.launch {
            favoriteNew = repository.getLikedNews()
        }
    }

    // Function to add a news article to the database
    fun addTechNewsToDB(article: Article) {
        viewModelScope.launch {
            val favoriteEntity = FavoriteEntity(
                id = 0,
                author = article.author!!,
                title = article.title,
                description = article.description!!,
                url = article.url,
                content = article.content!!,
                publishedAt = article.publishedAt!!,
                isLiked = true,
                urlToImage = article.urlToImage!!
            )
            try {
                repository.insertFavorite(favoriteEntity)
            } catch (e: Exception) {
                Log.e("Add to database", "Error adding to database: ${e.message}")
            }
        }
    }

    fun removeBtcNewsFromDB(article: Article) {
        viewModelScope.launch {
            try {
                val existingFavorite = repository.getLikedNews().find { it.title == article.title }
                if (existingFavorite != null) {
                    repository.deleteFavorite(existingFavorite)
                    Log.d("Database", "Deleted from favorites: ${article.title}")
                    _favoriteRemovedLiveData.postValue(article.title)
                } else {
                    Log.e("Database", "Error: Article not found in DB!")
                }
            } catch (e: Exception) {
                Log.e("Database", "Error removing from database: ${e.message}")
            }
        }
    }
}