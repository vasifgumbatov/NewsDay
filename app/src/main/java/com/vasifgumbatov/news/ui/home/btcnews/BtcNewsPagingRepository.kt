package com.vasifgumbatov.news.ui.home.btcnews

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import javax.inject.Inject

class BtcNewsPagingRepository @Inject constructor(
    private val newsDataSource: NewsDataSource
){

    fun getPagedBtcNews(query: String, apiKey: String) = Pager(
        config = PagingConfig(
            pageSize = 30, // Must match the custom pageSize in PagingSource
            enablePlaceholders = false
        ),
        pagingSourceFactory = { BtcNewsPagingSource(newsDataSource, query, apiKey) }
    ).liveData
}