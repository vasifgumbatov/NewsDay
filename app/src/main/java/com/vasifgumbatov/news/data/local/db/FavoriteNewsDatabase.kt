package com.vasifgumbatov.news.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vasifgumbatov.news.data.local.dao.FavoriteNewsDao
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 2, exportSchema = false)
abstract class FavoriteNewsDatabase : RoomDatabase() {
    abstract fun favoriteNewsDao(): FavoriteNewsDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteNewsDatabase? = null

        fun getDatabase(context: Context): FavoriteNewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteNewsDatabase::class.java,
                    "favorite_news_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}