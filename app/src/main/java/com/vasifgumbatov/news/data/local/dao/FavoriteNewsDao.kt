package com.vasifgumbatov.news.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity

@Dao
interface FavoriteNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite_table WHERE isLiked = 1")
    suspend fun getLikedNews(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite_table")
    suspend fun getAllNews(): List<FavoriteEntity>
}