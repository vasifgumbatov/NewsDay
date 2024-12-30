package com.vasifgumbatov.news.ui.home.btcnews

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vasifgumbatov.news.data.remote.response.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsPagingRepository @Inject constructor(
    private val pagingSource: NewsPagingSource
){
    fun getPagedNews() : Flow<PagingData<Article>> {
        val pagingConfig = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        )
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { pagingSource }
        ).flow
    }
}