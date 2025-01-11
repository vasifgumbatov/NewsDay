package com.vasifgumbatov.news.data.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.data.local.dao.FavoriteNewsDao
import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import com.vasifgumbatov.news.ui.home.btcnews.BtcNewsPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteNewsRepository @Inject constructor(
    private val favoriteDAO: FavoriteNewsDao,
) {

    suspend fun insertFavorite(favorite: FavoriteEntity) {
        favoriteDAO.insertFavorite(favorite)
    }

    suspend fun deleteFavorite(favorite: FavoriteEntity) {
        favoriteDAO.deleteFavorite(favorite)
    }

    suspend fun getLikedNews(): List<FavoriteEntity> {
        return favoriteDAO.getLikedNews()
    }

    suspend fun getAllNews(): List<FavoriteEntity> {
        return favoriteDAO.getAllNews()
    }
}