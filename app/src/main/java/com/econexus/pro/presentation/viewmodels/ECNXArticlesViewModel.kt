package com.econexus.pro.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.econexus.pro.domain.model.ECNXArticle
import com.econexus.pro.domain.repository.ECNXServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ECNXArticlesUiState(
    val articles: List<ECNXArticle> = emptyList(),
    val selectedArticle: ECNXArticle? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class ECNXArticlesViewModel @Inject constructor(
    private val repository: ECNXServicesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ECNXArticlesUiState(isLoading = true))
    val uiState: StateFlow<ECNXArticlesUiState> = _uiState.asStateFlow()

    init {
        loadArticles()
    }

    private fun loadArticles() {
        _uiState.value = ECNXArticlesUiState(
            articles = repository.getAllArticles(),
            isLoading = false
        )
    }

    fun loadArticleDetails(articleId: Int) {
        val article = repository.getArticleById(articleId)
        _uiState.value = _uiState.value.copy(selectedArticle = article)
    }
}