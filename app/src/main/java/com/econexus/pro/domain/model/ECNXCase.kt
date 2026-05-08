package com.econexus.pro.domain.model

data class ECNXCase(
    val id: Int,
    val title: String,
    val sector: String,
    val summary: String,
    val challenge: String,
    val solution: String,
    val result: String,
    val imageUrl: String,
    val tags: List<String>
)
