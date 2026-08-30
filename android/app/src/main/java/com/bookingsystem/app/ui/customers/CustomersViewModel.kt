package com.bookingsystem.app.ui.customers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bookingsystem.app.data.model.CreateCustomerRequest
import com.bookingsystem.app.data.model.Customer
import com.bookingsystem.app.data.repository.ApiResult
import com.bookingsystem.app.data.repository.CustomersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CustomersUiState(val isLoading: Boolean = false, val isSaving: Boolean = false, val customers: List<Customer> = emptyList(), val errorMessage: String? = null)
class CustomersViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CustomersRepository(application)
    private val _uiState = MutableStateFlow(CustomersUiState(isLoading = true))
    val uiState: StateFlow<CustomersUiState> = _uiState.asStateFlow()
    init { loadCustomers() }
    fun loadCustomers(search: String? = null) { _uiState.value = _uiState.value.copy(isLoading = true); viewModelScope.launch { when (val r = repository.getCustomers(search)) { is ApiResult.Success -> _uiState.value = CustomersUiState(customers = r.data); is ApiResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = r.message) } } }
    fun addCustomer(name: String, phone: String, onDone: () -> Unit) { if (name.isBlank() || phone.isBlank()) return; _uiState.value = _uiState.value.copy(isSaving = true); viewModelScope.launch { when (val r = repository.createCustomer(CreateCustomerRequest(name, phone))) { is ApiResult.Success -> { onDone(); loadCustomers() }; is ApiResult.Error -> _uiState.value = _uiState.value.copy(isSaving = false, errorMessage = r.message) } } }
    fun deleteCustomer(id: String) = viewModelScope.launch { when (val r = repository.deleteCustomer(id)) { is ApiResult.Success -> loadCustomers(); is ApiResult.Error -> _uiState.value = _uiState.value.copy(errorMessage = r.message) } }
}
