package com.econexus.pro.domain.model

data class ECNXService(
    val id: Int,
    val title: String,
    val category: String,
    val shortDescription: String,
    val fullDescription: String,
    val imageUrl: String,
    val benefits: List<String>,
    val deliverables: List<String>,
    val isFeatured: Boolean = false
)