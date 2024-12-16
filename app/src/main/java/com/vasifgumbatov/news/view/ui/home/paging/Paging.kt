//package com.vasifgumbatov.news.view.ui.home.paging
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.vasifgumbatov.news.data.remote.api.NewsDataSource
//import com.vasifgumbatov.news.data.remote.response.Article
//import javax.inject.Inject
//
//class Paging @Inject constructor(
//    private val newsDataSource: NewsDataSource,
//) : PagingSource<Int, Article>() {
//
//    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
//        return 0
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
//    }
//}