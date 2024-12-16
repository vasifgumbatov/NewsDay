package com.vasifgumbatov.news.view.ui.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.data.local.repository.FavoriteNewsRepository
import com.vasifgumbatov.news.data.remote.response.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsVM @Inject constructor(
    private val repository: FavoriteNewsRepository
) : ViewModel() {

    val newsLiveData = MutableLiveData<List<FavoriteEntity>>()

    fun getFavorites() {
        viewModelScope.launch {
            newsLiveData.postValue(repository.getAllNews())
        }
    }

    fun insertFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    }

    fun getLikedNews() {
        viewModelScope.launch {
            newsLiveData.postValue(repository.getLikedNews())
        }
    }

    fun deleteFromDB(favorite: FavoriteEntity) {
        viewModelScope.launch {
            repository.insertFavorite(
                FavoriteEntity(
                    id = 0,
                    author = favorite.author,
                    title = favorite.title,
                    description = favorite.description,
                    url = favorite.url,
                    content = favorite.content,
                    publishedAt = favorite.publishedAt,
                    isLiked = true,
                    urlToImage = favorite.urlToImage
                )
            )
        }
    }
}