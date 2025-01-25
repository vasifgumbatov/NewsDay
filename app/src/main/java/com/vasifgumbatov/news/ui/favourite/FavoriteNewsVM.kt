package com.vasifgumbatov.news.ui.favourite

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.data.local.repository.FavoriteNewsRepository
import com.vasifgumbatov.news.ui.home.HomeNewsVM
import com.vasifgumbatov.news.ui.home.btcnews.BtcNewsVM
import com.vasifgumbatov.news.ui.home.usanews.USANewsVM
import com.vasifgumbatov.news.ui.home.techcrunchnews.TechCrunchVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsVM @Inject constructor(
    private val repository: FavoriteNewsRepository
) : ViewModel() {

    val newsLiveData = MutableLiveData<List<FavoriteEntity>>()

    // Get all favorite news from the database
    fun getFavorites() {
        viewModelScope.launch {
            newsLiveData.postValue(repository.getAllNews())
        }
    }

    // Delete favorite from the database
    fun deleteFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            try {
                repository.deleteFavorite(favorite)
                Log.d("FavoriteVM", "Deleted from favorites: ${favorite.title}")
                getFavorites() // Refresh the list
            } catch (e: Exception) {
                Log.e("FavoriteVM", "Error deleting from favorites: ${e.message}")
            }
        }
    }

    // Observe deletions from BtcNewsVM
    fun observeDeletions(btcNewsVM: BtcNewsVM, techNewsVM: TechCrunchVM, homeNewsVM: HomeNewsVM, USANewsVM: USANewsVM) {
        btcNewsVM.favoriteRemovedLiveData.observeForever { deletedTitle ->
            Log.d("FavoriteVM", "Removing $deletedTitle from favorites")
            viewModelScope.launch {
                val favoriteToRemove = repository.getAllNews().find { it.title == deletedTitle }
                if (favoriteToRemove != null) {
                    deleteFavorite(favoriteToRemove)
                }
            }
        }

        techNewsVM.favoriteRemovedLiveData.observeForever { deletedTitle ->
            Log.d("FavoriteVM", "Removing $deletedTitle from favorites")
            viewModelScope.launch {
                val favoriteToRemove = repository.getAllNews().find { it.title == deletedTitle }
                if (favoriteToRemove != null) {
                    deleteFavorite(favoriteToRemove)
                }
            }
        }

        homeNewsVM.favoriteRemovedLiveData.observeForever { deletedTitle ->
            Log.d("FavoriteVM", "Removing $deletedTitle from favorites")
            viewModelScope.launch {
                val favoriteToRemove = repository.getAllNews().find { it.title == deletedTitle }
                if (favoriteToRemove != null) {
                    deleteFavorite(favoriteToRemove)
                }
            }
        }

        USANewsVM.favoriteRemovedLiveData.observeForever { deletedTitle ->
            Log.d("FavoriteVM", "Removing $deletedTitle from favorites")
            viewModelScope.launch {
                val favoriteToRemove = repository.getAllNews().find { it.title == deletedTitle }
                if (favoriteToRemove != null) {
                    deleteFavorite(favoriteToRemove)
                }
            }
        }
    }
}