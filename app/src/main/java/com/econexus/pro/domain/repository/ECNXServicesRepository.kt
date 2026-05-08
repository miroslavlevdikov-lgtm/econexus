package com.econexus.pro.domain.repository

import com.econexus.pro.domain.model.ECNXArticle
import com.econexus.pro.domain.model.ECNXCase
import com.econexus.pro.domain.model.ECNXService

interface ECNXServicesRepository {
    fun getAllServices(): List<ECNXService>
    fun getServiceById(id: Int): ECNXService?
    fun getFeaturedServices(): List<ECNXService>
    fun getServiceCategories(): List<String>
    fun getServiceTitles(): List<String>
    fun getServicesByCategory(category: String): List<ECNXService>

    fun getAllCases(): List<ECNXCase>
    fun getCaseById(id: Int): ECNXCase?

    fun getAllArticles(): List<ECNXArticle>
    fun getArticleById(id: Int): ECNXArticle?
}