package com.vasifgumbatov.news.ui.home.btcnews

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import com.vasifgumbatov.news.data.remote.response.Article
import javax.inject.Inject

class BtcNewsPagingSource @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val query: String,
    private val apiKey: String,
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        val customPageSize = 30
        return try {
            val response = newsDataSource.getEverythingBTC(
                apiKey = apiKey,
                query = query,
                pageSize = customPageSize,
                page = page
            )

            val articles = response.body()?.articles ?: emptyList()

            val nextKey = if (page >= 3 || articles.isEmpty()) null else page + 1

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}