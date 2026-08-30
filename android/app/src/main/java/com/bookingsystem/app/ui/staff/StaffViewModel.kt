package com.bookingsystem.app.ui.staff

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bookingsystem.app.data.model.CreateStaffRequest
import com.bookingsystem.app.data.model.Staff
import com.bookingsystem.app.data.repository.ApiResult
import com.bookingsystem.app.data.repository.StaffRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StaffUiState(val isLoading: Boolean = false, val isSaving: Boolean = false, val staff: List<Staff> = emptyList(), val errorMessage: String? = null)
class StaffViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StaffRepository(application)
    private val _uiState = MutableStateFlow(StaffUiState(isLoading = true))
    val uiState: StateFlow<StaffUiState> = _uiState.asStateFlow()
    init { loadStaff() }
    fun loadStaff() { _uiState.value = _uiState.value.copy(isLoading = true); viewModelScope.launch { when (val r = repository.getStaff()) { is ApiResult.Success -> _uiState.value = StaffUiState(staff = r.data); is ApiResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = r.message) } } }
    fun addStaff(name: String, phone: String, onDone: () -> Unit) { if (name.isBlank()) return; _uiState.value = _uiState.value.copy(isSaving = true); viewModelScope.launch { when (val r = repository.createStaff(CreateStaffRequest(name, phone.ifBlank { null }))) { is ApiResult.Success -> { onDone(); loadStaff() }; is ApiResult.Error -> _uiState.value = _uiState.value.copy(isSaving = false, errorMessage = r.message) } } }
    fun deleteStaff(id: String) = viewModelScope.launch { when (val r = repository.deleteStaff(id)) { is ApiResult.Success -> loadStaff(); is ApiResult.Error -> _uiState.value = _uiState.value.copy(errorMessage = r.message) } }
}
