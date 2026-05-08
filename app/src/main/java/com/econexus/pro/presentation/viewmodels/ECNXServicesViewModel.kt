package com.econexus.pro.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.econexus.pro.domain.model.ECNXService
import com.econexus.pro.domain.repository.ECNXServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ECNXServicesUiState(
    val services: List<ECNXService> = emptyList(),
    val filteredServices: List<ECNXService> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val selectedService: ECNXService? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class ECNXServicesViewModel @Inject constructor(
    private val repository: ECNXServicesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ECNXServicesUiState(isLoading = true))
    val uiState: StateFlow<ECNXServicesUiState> = _uiState.asStateFlow()

    init {
        loadServices()
    }

    private fun loadServices() {
        val allServices = repository.getAllServices()
        val categories = repository.getServiceCategories()
        _uiState.value = ECNXServicesUiState(
            services = allServices,
            filteredServices = allServices,
            categories = categories,
            isLoading = false
        )
    }

    fun onCategorySelected(category: String?) {
        val current = _uiState.value
        val filtered = if (category == null || category == current.selectedCategory) {
            current.services
        } else {
            current.services.filter { it.category == category }
        }
        _uiState.value = current.copy(
            filteredServices = filtered,
            selectedCategory = if (category == current.selectedCategory) null else category
        )
    }

    fun loadServiceDetails(serviceId: Int) {
        val service = repository.getServiceById(serviceId)
        _uiState.value = _uiState.value.copy(selectedService = service)
    }
}