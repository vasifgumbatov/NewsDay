package com.vasifgumbatov.news.ui.home.business

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.data.local.repository.FavoriteNewsRepository
import com.vasifgumbatov.news.data.remote.interactor.GetNewsUseCase
import com.vasifgumbatov.news.data.remote.response.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessVM @Inject constructor(
    private val repository: FavoriteNewsRepository,
    private val getNewsUseCase: GetNewsUseCase,
) : ViewModel() {

    val newsLiveData = MutableLiveData<List<Article>>()
    val errorLiveData = MutableLiveData<String>()
    private var favoriteNew = listOf<FavoriteEntity>()

    // Initialize the favorite news list
    init {
        getFavoriteNews()
    }

    fun fetchNewsBusiness(country: String, category: String, apiKey: String) {
        viewModelScope.launch {
            val newsResponse = getNewsUseCase.executeBusinessNews(
                country, category, apiKey
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

    // Function to get favorite news from the database
    private fun getFavoriteNews() {
        viewModelScope.launch {
            favoriteNew = repository.getLikedNews()
        }
    }

    // Function to add a news article to the database
    fun addBtcNewsToDB(article: Article) {
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