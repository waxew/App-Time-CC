package com.bookingsystem.app.ui.appointments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bookingsystem.app.data.model.*
import com.bookingsystem.app.data.repository.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddAppointmentUiState(
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val customers: List<Customer> = emptyList(),
    val services: List<Service> = emptyList(),
    val staff: List<Staff> = emptyList(),
    val errorMessage: String? = null
)

class AddAppointmentViewModel(application: Application) : AndroidViewModel(application) {
    private val appointmentsRepository = AppointmentsRepository(application)
    private val customersRepository = CustomersRepository(application)
    private val servicesRepository = ServicesRepository(application)
    private val staffRepository = StaffRepository(application)
    private val _uiState = MutableStateFlow(AddAppointmentUiState())
    val uiState: StateFlow<AddAppointmentUiState> = _uiState.asStateFlow()
    init { loadOptions() }
    private fun loadOptions() = viewModelScope.launch {
        coroutineScope {
            val c = async { customersRepository.getCustomers() }
            val s = async { servicesRepository.getServices() }
            val p = async { staffRepository.getStaff() }
            _uiState.value = AddAppointmentUiState(
                isLoading = false,
                customers = (c.await() as? ApiResult.Success)?.data ?: emptyList(),
                services = (s.await() as? ApiResult.Success)?.data ?: emptyList(),
                staff = (p.await() as? ApiResult.Success)?.data ?: emptyList()
            )
        }
    }
    fun submit(customerId: String?, serviceId: String?, staffId: String?, startTimeIso: String?, notes: String?, onSuccess: () -> Unit) {
        if (serviceId == null || startTimeIso.isNullOrBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "انتخاب خدمت و تاریخ/ساعت الزامی است")
            return
        }
        _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
        viewModelScope.launch {
            when (val result = appointmentsRepository.createAppointment(CreateAppointmentRequest(customerId, serviceId, staffId, startTimeIso, notes?.ifBlank { null }))) {
                is ApiResult.Success -> { _uiState.value = _uiState.value.copy(isSaving = false); onSuccess() }
                is ApiResult.Error -> _uiState.value = _uiState.value.copy(isSaving = false, errorMessage = result.message)
            }
        }
    }
}
