package com.econexus.pro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.econexus.pro.data.local.ECNXPreferencesRepository
import com.econexus.pro.presentation.navigation.ECNXNavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ECNXMainUiState(
    val startDestination: String? = null
)

@HiltViewModel
class ECNXMainViewModel @Inject constructor(
    private val preferencesRepository: ECNXPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ECNXMainUiState())
    val uiState: StateFlow<ECNXMainUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.isOnboardingCompleted.collect { isCompleted ->
                _uiState.value = ECNXMainUiState(
                    startDestination = if (isCompleted) ECNXNavRoutes.HOME else ECNXNavRoutes.ONBOARDING
                )
            }
        }
    }
}