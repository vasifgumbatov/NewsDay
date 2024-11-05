package com.vasifgumbatov.news.data.remote.response

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val id: String,
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val content: String?,
    val publishedAt: String?,
    var isLiked: Boolean = false
)

data class Source(
    val id: String?,
    val name: String
)
