package com.vasifgumbatov.news.di

import android.content.Context
import com.vasifgumbatov.news.data.local.dao.FavoriteNewsDao
import com.vasifgumbatov.news.data.local.db.FavoriteNewsDatabase
import com.vasifgumbatov.news.data.local.repository.FavoriteNewsRepository
import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import com.vasifgumbatov.news.data.remote.api.WeatherDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @NewsRetrofit
    fun provideNewsRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideNewsApiService(@NewsRetrofit retrofit: Retrofit): NewsDataSource {
        return retrofit.create(NewsDataSource::class.java)
    }

    @Provides
    @Singleton
    @WeatherRetrofit
    fun provideWeatherRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideWeatherApiService(@WeatherRetrofit retrofit: Retrofit): WeatherDataSource {
        return retrofit.create(WeatherDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoriteNewsDao(db: FavoriteNewsDatabase): FavoriteNewsDao {
        return db.favoriteNewsDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteNewsRepository(
        favoriteDao: FavoriteNewsDao,
    ): FavoriteNewsRepository {
        return FavoriteNewsRepository(favoriteDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): FavoriteNewsDatabase {
        return FavoriteNewsDatabase.getDatabase(appContext)
    }
}