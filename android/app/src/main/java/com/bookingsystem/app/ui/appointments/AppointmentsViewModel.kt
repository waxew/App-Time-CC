package com.bookingsystem.app.ui.appointments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bookingsystem.app.data.model.Appointment
import com.bookingsystem.app.data.model.UpdateAppointmentRequest
import com.bookingsystem.app.data.repository.ApiResult
import com.bookingsystem.app.data.repository.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppointmentsUiState(val isLoading: Boolean = false, val appointments: List<Appointment> = emptyList(), val errorMessage: String? = null)

class AppointmentsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppointmentsRepository(application)
    private val _uiState = MutableStateFlow(AppointmentsUiState(isLoading = true))
    val uiState: StateFlow<AppointmentsUiState> = _uiState.asStateFlow()
    init { loadAppointments() }
    fun loadAppointments() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            when (val result = repository.getAppointments()) {
                is ApiResult.Success -> _uiState.value = AppointmentsUiState(appointments = result.data)
                is ApiResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
            }
        }
    }
    fun updateStatus(id: String, status: String) = viewModelScope.launch {
        when (val result = repository.updateAppointment(id, UpdateAppointmentRequest(status = status))) {
            is ApiResult.Success -> loadAppointments()
            is ApiResult.Error -> _uiState.value = _uiState.value.copy(errorMessage = result.message)
        }
    }
    fun deleteAppointment(id: String) = viewModelScope.launch {
        when (val result = repository.deleteAppointment(id)) {
            is ApiResult.Success -> loadAppointments()
            is ApiResult.Error -> _uiState.value = _uiState.value.copy(errorMessage = result.message)
        }
    }
}
