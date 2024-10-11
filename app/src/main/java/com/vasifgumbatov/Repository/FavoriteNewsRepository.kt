package com.vasifgumbatov.Repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vasifgumbatov.RoomDataBase.FavoriteNews
import com.vasifgumbatov.RoomDataBase.FavoriteNewsDao
import com.vasifgumbatov.RoomDataBase.FavoriteNewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteNewsRepository(private val dao: FavoriteNewsDao) {
    val allFavorites: LiveData<List<FavoriteNews>> = dao.getAllFavorites()

    suspend fun insert(news: FavoriteNews) {
        dao.insertFavorite(news)
    }

    suspend fun delete(news: FavoriteNews) {
        dao.deleteFavorite(news)
    }
}

class FavoriteNewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteNewsRepository
    val allFavorites: LiveData<List<FavoriteNews>>

    init {
        val favoriteNewsDao = FavoriteNewsDatabase.getDatabase(application).favoriteNewsDao()
        repository = FavoriteNewsRepository(favoriteNewsDao)
        allFavorites = repository.allFavorites
    }

    fun insert(news: FavoriteNews) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(news)
    }

    fun delete(news: FavoriteNews) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(news)
    }
}