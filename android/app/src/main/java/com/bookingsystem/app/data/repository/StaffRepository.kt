package com.bookingsystem.app.data.repository

import android.content.Context
import com.bookingsystem.app.data.model.CreateStaffRequest
import com.bookingsystem.app.data.model.Staff
import com.bookingsystem.app.data.remote.RetrofitClient

class StaffRepository(context: Context) {
    private val api = RetrofitClient.getApiService(context)
    suspend fun getStaff(): ApiResult<List<Staff>> = safeApiCall { api.getStaff() }
    suspend fun createStaff(request: CreateStaffRequest): ApiResult<Staff> = safeApiCall { api.createStaff(request) }
    suspend fun deleteStaff(id: String): ApiResult<Unit> = safeActionCall { api.deleteStaff(id) }
}
