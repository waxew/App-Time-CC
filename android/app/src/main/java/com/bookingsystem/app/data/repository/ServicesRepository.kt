package com.bookingsystem.app.data.repository

import android.content.Context
import com.bookingsystem.app.data.model.CreateServiceRequest
import com.bookingsystem.app.data.model.Service
import com.bookingsystem.app.data.remote.RetrofitClient

class ServicesRepository(context: Context) {
    private val api = RetrofitClient.getApiService(context)
    suspend fun getServices(): ApiResult<List<Service>> = safeApiCall { api.getServices() }
    suspend fun createService(request: CreateServiceRequest): ApiResult<Service> = safeApiCall { api.createService(request) }
    suspend fun updateService(id: String, request: CreateServiceRequest): ApiResult<Service> = safeApiCall { api.updateService(id, request) }
    suspend fun deleteService(id: String): ApiResult<Unit> = safeActionCall { api.deleteService(id) }
}
