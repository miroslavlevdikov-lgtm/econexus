package com.econexus.pro.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.econexus.pro.domain.model.ECNXBooking
import com.econexus.pro.domain.repository.ECNXBookingRepository
import com.econexus.pro.domain.repository.ECNXServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class ECNXBookingUiState(
    val fullName: String = "",
    val companyName: String = "",
    val phone: String = "",
    val email: String = "",
    val selectedService: String = "",
    val format: String = "Online meeting",
    val date: String = "",
    val time: String = "",
    val address: String = "",
    val comment: String = "",
    val serviceOptions: List<String> = emptyList(),
    val errors: Map<String, String> = emptyMap(),
    val isSubmitting: Boolean = false,
    val submittedBookingId: Long? = null,
    val confirmedBooking: ECNXBooking? = null
)

@HiltViewModel
class ECNXBookingViewModel @Inject constructor(
    private val bookingRepository: ECNXBookingRepository,
    private val servicesRepository: ECNXServicesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ECNXBookingUiState())
    val uiState: StateFlow<ECNXBookingUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = _uiState.value.copy(
            serviceOptions = servicesRepository.getServiceTitles()
        )
    }

    fun onFullNameChange(value: String) {
        _uiState.value = _uiState.value.copy(
            fullName = value,
            errors = _uiState.value.errors - "fullName"
        )
    }

    fun onCompanyNameChange(value: String) {
        _uiState.value = _uiState.value.copy(
            companyName = value,
            errors = _uiState.value.errors - "companyName"
        )
    }

    fun onPhoneChange(value: String) {
        _uiState.value = _uiState.value.copy(
            phone = value,
            errors = _uiState.value.errors - "phone"
        )
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(
            email = value,
            errors = _uiState.value.errors - "email"
        )
    }

    fun onServiceSelected(service: String) {
        _uiState.value = _uiState.value.copy(
            selectedService = service,
            errors = _uiState.value.errors - "selectedService"
        )
    }

    fun onFormatChange(format: String) {
        _uiState.value = _uiState.value.copy(format = format)
    }

    fun onDateChange(date: String) {
        _uiState.value = _uiState.value.copy(
            date = date,
            errors = _uiState.value.errors - "date"
        )
    }

    fun onTimeChange(time: String) {
        _uiState.value = _uiState.value.copy(
            time = time,
            errors = _uiState.value.errors - "time"
        )
    }

    fun onAddressChange(value: String) {
        _uiState.value = _uiState.value.copy(address = value)
    }

    fun onCommentChange(value: String) {
        _uiState.value = _uiState.value.copy(comment = value)
    }

    fun setInitialService(serviceTitle: String?) {
        if (!serviceTitle.isNullOrBlank()) {
            _uiState.value = _uiState.value.copy(selectedService = serviceTitle)
        }
    }

    fun submitBooking() {
        val state = _uiState.value
        val errors = mutableMapOf<String, String>()

        if (state.fullName.isBlank()) errors["fullName"] = "Full name is required"
        if (state.companyName.isBlank()) errors["companyName"] = "Company name is required"
        if (state.phone.isBlank()) errors["phone"] = "Phone number is required"
        if (state.email.isBlank()) {
            errors["email"] = "Email is required"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            errors["email"] = "Invalid email format"
        }
        if (state.selectedService.isBlank()) errors["selectedService"] = "Please select a service"
        if (state.date.isBlank()) errors["date"] = "Date is required"
        if (state.time.isBlank()) errors["time"] = "Time is required"

        if (errors.isNotEmpty()) {
            _uiState.value = state.copy(errors = errors)
            return
        }

        _uiState.value = state.copy(isSubmitting = true, errors = emptyMap())

        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val datePart = dateFormat.format(Date())
        val randomPart = (1000..9999).random()
        val bookingNumber = "ECNX-$datePart-$randomPart"

        val booking = ECNXBooking(
            bookingNumber = bookingNumber,
            fullName = state.fullName,
            companyName = state.companyName,
            phone = state.phone,
            email = state.email,
            serviceTitle = state.selectedService,
            format = state.format,
            date = state.date,
            time = state.time,
            address = state.address.ifBlank { null },
            comment = state.comment.ifBlank { null }
        )

        viewModelScope.launch {
            val id = bookingRepository.insertBooking(booking)
            _uiState.value = _uiState.value.copy(
                isSubmitting = false,
                submittedBookingId = id
            )
        }
    }

    fun loadBookingConfirmation(bookingId: Long) {
        viewModelScope.launch {
            val booking = bookingRepository.getBookingById(bookingId)
            _uiState.value = _uiState.value.copy(confirmedBooking = booking)
        }
    }

    fun resetForm() {
        _uiState.value = ECNXBookingUiState(
            serviceOptions = servicesRepository.getServiceTitles()
        )
    }
}