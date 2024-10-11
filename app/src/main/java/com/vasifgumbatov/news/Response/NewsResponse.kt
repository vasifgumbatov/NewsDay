package com.vasifgumbatov.news.Response

import com.google.gson.annotations.SerializedName

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
    val publishedAt: String?
)

data class Source(
    val id: String?,
    val name: String
)

data class CompanyNews(
    val category: String,
    val headline: String,
    val image: String,
    val source: String,
    val summary: String,
    val url: String
)
