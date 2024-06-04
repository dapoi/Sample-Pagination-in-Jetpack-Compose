package com.dapascript.newscompose.data.entity

data class EduNewsPage(
    val page: Int,
    val results: List<EduNewsEntity>,
    val totalPages: Int
)

data class EduNewsEntity(
    val title: String? = null,
    val author: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)