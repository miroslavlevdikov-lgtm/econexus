package com.econexus.pro.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.econexus.pro.domain.model.ECNXBooking
import com.econexus.pro.domain.repository.ECNXBookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ECNXSettingsUiState(
    val bookings: List<ECNXBooking> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class ECNXSettingsViewModel @Inject constructor(
    private val bookingRepository: ECNXBookingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ECNXSettingsUiState(isLoading = true))
    val uiState: StateFlow<ECNXSettingsUiState> = _uiState.asStateFlow()

    init {
        loadBookings()
    }

    private fun loadBookings() {
        viewModelScope.launch {
            bookingRepository.getAllBookings().collect { bookings ->
                _uiState.value = ECNXSettingsUiState(
                    bookings = bookings,
                    isLoading = false
                )
            }
        }
    }
}