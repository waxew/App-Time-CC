package com.bookingsystem.app.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bookingsystem.app.data.model.LoginRequest
import com.bookingsystem.app.data.model.RegisterRequest
import com.bookingsystem.app.data.repository.ApiResult
import com.bookingsystem.app.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository(application)
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(phone: String, password: String, onSuccess: () -> Unit) {
        if (phone.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState(errorMessage = "شماره موبایل و رمز عبور رو وارد کنید")
            return
        }
        _uiState.value = AuthUiState(isLoading = true)
        viewModelScope.launch {
            when (val result = repository.login(LoginRequest(phone, password))) {
                is ApiResult.Success -> { _uiState.value = AuthUiState(); onSuccess() }
                is ApiResult.Error -> _uiState.value = AuthUiState(errorMessage = result.message)
            }
        }
    }

    fun register(businessName: String, ownerName: String, phone: String, password: String, onSuccess: () -> Unit) {
        if (businessName.isBlank() || phone.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState(errorMessage = "نام کسب‌وکار، شماره موبایل و رمز عبور الزامی است")
            return
        }
        _uiState.value = AuthUiState(isLoading = true)
        viewModelScope.launch {
            val request = RegisterRequest(name = businessName, ownerName = ownerName.ifBlank { null }, phone = phone, password = password)
            when (val result = repository.register(request)) {
                is ApiResult.Success -> { _uiState.value = AuthUiState(); onSuccess() }
                is ApiResult.Error -> _uiState.value = AuthUiState(errorMessage = result.message)
            }
        }
    }
}
