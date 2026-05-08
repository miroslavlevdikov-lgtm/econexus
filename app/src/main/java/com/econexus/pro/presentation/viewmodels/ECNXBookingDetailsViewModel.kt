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

data class ECNXBookingDetailsUiState(
    val booking: ECNXBooking? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ECNXBookingDetailsViewModel @Inject constructor(
    private val bookingRepository: ECNXBookingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ECNXBookingDetailsUiState(isLoading = true))
    val uiState: StateFlow<ECNXBookingDetailsUiState> = _uiState.asStateFlow()

    fun loadBooking(bookingId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val booking = bookingRepository.getBookingById(bookingId)
            if (booking != null) {
                _uiState.value = ECNXBookingDetailsUiState(booking = booking, isLoading = false)
            } else {
                _uiState.value = ECNXBookingDetailsUiState(isLoading = false, error = "Booking not found")
            }
        }
    }
}