package com.vasifgumbatov.news.ui.home.btcnews

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import com.vasifgumbatov.news.data.remote.response.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsPagingRepository @Inject constructor(
//    private val pagingSource: NewsPagingSource
    private val newsDataSource: NewsDataSource
){
    fun getPagedNews(query: String, apiKey: String) : Flow<PagingData<Article>> {
        val pagingConfig = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            initialLoadSize = 10
        )
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { NewsPagingSource(newsDataSource, query, apiKey) }
        ).flow
    }
}