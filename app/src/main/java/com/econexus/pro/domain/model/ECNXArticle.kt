package com.econexus.pro.domain.model

data class ECNXArticle(
    val id: Int,
    val title: String,
    val category: String,
    val summary: String,
    val content: String,
    val imageUrl: String,
    val author: String,
    val publishedAt: String
)