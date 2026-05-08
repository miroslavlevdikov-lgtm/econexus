package com.econexus.pro.data.repository

import com.econexus.pro.data.mock.ECNXMockDataProvider
import com.econexus.pro.domain.model.ECNXArticle
import com.econexus.pro.domain.model.ECNXCase
import com.econexus.pro.domain.model.ECNXService
import com.econexus.pro.domain.repository.ECNXServicesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ECNXServicesRepositoryImpl @Inject constructor() : ECNXServicesRepository {

    override fun getAllServices(): List<ECNXService> = ECNXMockDataProvider.getServices()

    override fun getServiceById(id: Int): ECNXService? =
        ECNXMockDataProvider.getServices().find { it.id == id }

    override fun getFeaturedServices(): List<ECNXService> =
        ECNXMockDataProvider.getFeaturedServices()

    override fun getServiceCategories(): List<String> =
        ECNXMockDataProvider.getServiceCategories()

    override fun getServiceTitles(): List<String> =
        ECNXMockDataProvider.getServiceTitles()

    override fun getServicesByCategory(category: String): List<ECNXService> =
        ECNXMockDataProvider.getServices().filter { it.category == category }

    override fun getAllCases(): List<ECNXCase> = ECNXMockDataProvider.getCases()

    override fun getCaseById(id: Int): ECNXCase? =
        ECNXMockDataProvider.getCases().find { it.id == id }

    override fun getAllArticles(): List<ECNXArticle> = ECNXMockDataProvider.getArticles()

    override fun getArticleById(id: Int): ECNXArticle? =
        ECNXMockDataProvider.getArticles().find { it.id == id }
}