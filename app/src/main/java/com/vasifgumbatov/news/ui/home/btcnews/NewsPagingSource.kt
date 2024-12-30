package com.vasifgumbatov.news.ui.home.btcnews

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vasifgumbatov.news.data.remote.api.NewsDataSource
import com.vasifgumbatov.news.data.remote.response.Article
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val newsDataSource: NewsDataSource
): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val currentPage = params.key ?: 1 // Start from the first page
        return try {
            val response = newsDataSource.getEverythingBTC(
                apiKey = "331cc6318d5f4e4bbdddfe9f3d4d6a93",
                query = "bitcoin",
                page = currentPage,
                pageSize = 10
            )

            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()
                LoadResult.Page(
                    data = articles,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (articles.isEmpty()) null else currentPage + 1
                )
            } else {
                LoadResult.Error(Exception("Failed to load news"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}