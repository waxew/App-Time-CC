package com.bookingsystem.app.ui.services

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bookingsystem.app.data.model.CreateServiceRequest
import com.bookingsystem.app.data.model.Service
import com.bookingsystem.app.data.repository.ApiResult
import com.bookingsystem.app.data.repository.ServicesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ServicesUiState(val isLoading: Boolean = false, val isSaving: Boolean = false, val services: List<Service> = emptyList(), val errorMessage: String? = null)
class ServicesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ServicesRepository(application)
    private val _uiState = MutableStateFlow(ServicesUiState(isLoading = true))
    val uiState: StateFlow<ServicesUiState> = _uiState.asStateFlow()
    init { loadServices() }
    fun loadServices() { _uiState.value = _uiState.value.copy(isLoading = true); viewModelScope.launch { when (val r = repository.getServices()) { is ApiResult.Success -> _uiState.value = ServicesUiState(services = r.data); is ApiResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = r.message) } } }
    fun addService(name: String, duration: Int, price: Double?, onDone: () -> Unit) { if (name.isBlank()) return; _uiState.value = _uiState.value.copy(isSaving = true); viewModelScope.launch { when (val r = repository.createService(CreateServiceRequest(name, duration, price))) { is ApiResult.Success -> { onDone(); loadServices() }; is ApiResult.Error -> _uiState.value = _uiState.value.copy(isSaving = false, errorMessage = r.message) } } }
    fun deleteService(id: String) = viewModelScope.launch { when (val r = repository.deleteService(id)) { is ApiResult.Success -> loadServices(); is ApiResult.Error -> _uiState.value = _uiState.value.copy(errorMessage = r.message) } }
}
