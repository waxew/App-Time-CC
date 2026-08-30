package com.bookingsystem.app.data.repository

import android.content.Context
import com.bookingsystem.app.data.local.TokenManager
import com.bookingsystem.app.data.model.AuthResult
import com.bookingsystem.app.data.model.LoginRequest
import com.bookingsystem.app.data.model.RegisterRequest
import com.bookingsystem.app.data.remote.RetrofitClient

class AuthRepository(context: Context) {
    private val api = RetrofitClient.getApiService(context)
    private val tokenManager = TokenManager(context)
    suspend fun register(request: RegisterRequest): ApiResult<AuthResult> {
        val result = safeApiCall { api.register(request) }
        if (result is ApiResult.Success) tokenManager.saveToken(result.data.token)
        return result
    }
    suspend fun login(request: LoginRequest): ApiResult<AuthResult> {
        val result = safeApiCall { api.login(request) }
        if (result is ApiResult.Success) tokenManager.saveToken(result.data.token)
        return result
    }
    suspend fun logout() { tokenManager.clearToken() }
    suspend fun isLoggedIn(): Boolean = !tokenManager.getToken().isNullOrBlank()
}
