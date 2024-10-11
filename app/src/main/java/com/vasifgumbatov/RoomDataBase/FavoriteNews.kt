package com.vasifgumbatov.RoomDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_news")
data class FavoriteNews(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val publishedAt: String,
    val content: String
)
