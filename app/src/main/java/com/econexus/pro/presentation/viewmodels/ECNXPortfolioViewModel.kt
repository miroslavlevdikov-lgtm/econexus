package com.econexus.pro.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.econexus.pro.domain.model.ECNXCase
import com.econexus.pro.domain.repository.ECNXServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ECNXPortfolioUiState(
    val cases: List<ECNXCase> = emptyList(),
    val selectedCase: ECNXCase? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class ECNXPortfolioViewModel @Inject constructor(
    private val repository: ECNXServicesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ECNXPortfolioUiState(isLoading = true))
    val uiState: StateFlow<ECNXPortfolioUiState> = _uiState.asStateFlow()

    init {
        loadCases()
    }

    private fun loadCases() {
        _uiState.value = ECNXPortfolioUiState(
            cases = repository.getAllCases(),
            isLoading = false
        )
    }

    fun loadCaseDetails(caseId: Int) {
        val case = repository.getCaseById(caseId)
        _uiState.value = _uiState.value.copy(selectedCase = case)
    }
}