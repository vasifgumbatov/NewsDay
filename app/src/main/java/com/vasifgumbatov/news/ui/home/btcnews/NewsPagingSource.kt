package com.vasifgumbatov.news.ui.home.btcnews

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import com.vasifgumbatov.news.data.remote.response.Article
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val query: String,
    private val apiKey: String,
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1 // Default to the first page
        return try {
            val response = newsDataSource.getEverythingBTC(query, apiKey, page, params.loadSize)
            val articles = response.body()?.articles ?: emptyList()

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }
}