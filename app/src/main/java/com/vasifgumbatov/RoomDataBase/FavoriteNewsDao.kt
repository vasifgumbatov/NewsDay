package com.vasifgumbatov.RoomDataBase

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface FavoriteNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(news: FavoriteNews)

    @Delete
    suspend fun deleteFavorite(news: FavoriteNews)

    @Query("SELECT * FROM favorite_news")
    fun getAllFavorites(): LiveData<List<FavoriteNews>>
}