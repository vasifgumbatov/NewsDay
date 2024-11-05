package com.vasifgumbatov.news.data.local.repository

import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.data.local.dao.FavoriteNewsDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteNewsRepository @Inject constructor(private val favoriteDAO: FavoriteNewsDao) {

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