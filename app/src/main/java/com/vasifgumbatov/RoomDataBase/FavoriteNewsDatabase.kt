package com.vasifgumbatov.RoomDataBase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class FavoriteNewsDatabase : RoomDatabase() {

    abstract fun favoriteNewsDao(): FavoriteNewsDao

    companion object {
        @Volatile
        private var instance: FavoriteNewsDatabase? = null
        fun getDatabase(context: Context): FavoriteNewsDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteNewsDatabase::class.java,
                    "favorite_news_db"
                ).build().also { instance = it }
            }
        }
    }
}