package com.econexus.pro.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.econexus.pro.domain.model.ECNXArticle
import com.econexus.pro.domain.model.ECNXCase
import com.econexus.pro.domain.model.ECNXService
import com.econexus.pro.domain.repository.ECNXServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ECNXHomeUiState(
    val featuredServices: List<ECNXService> = emptyList(),
    val allServices: List<ECNXService> = emptyList(),
    val cases: List<ECNXCase> = emptyList(),
    val articles: List<ECNXArticle> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class ECNXHomeViewModel @Inject constructor(
    private val repository: ECNXServicesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ECNXHomeUiState(isLoading = true))
    val uiState: StateFlow<ECNXHomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = ECNXHomeUiState(
            featuredServices = repository.getFeaturedServices(),
            allServices = repository.getAllServices(),
            cases = repository.getAllCases().take(3),
            articles = repository.getAllArticles(),
            isLoading = false
        )
    }
}