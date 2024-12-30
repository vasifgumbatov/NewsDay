package com.vasifgumbatov.news.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val id: Int,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val content: String,
    val publishedAt: String,
    var isLiked: Boolean = false,
    var isDelete: Boolean = false,
    val urlToImage: String
)
