package com.ryannm.newsretriever.model

import java.util.Date

data class NewsItemModel (
    val source: Source? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: Date? = null,
    val content: String? = null,
)

data class Source (
    val id: String? = null,
    val name: String? = null
)