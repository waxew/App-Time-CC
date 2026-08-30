package com.bookingsystem.app.data.repository

import android.content.Context
import com.bookingsystem.app.data.model.CreateCustomerRequest
import com.bookingsystem.app.data.model.Customer
import com.bookingsystem.app.data.remote.RetrofitClient

class CustomersRepository(context: Context) {
    private val api = RetrofitClient.getApiService(context)
    suspend fun getCustomers(search: String? = null): ApiResult<List<Customer>> = safeApiCall { api.getCustomers(search) }
    suspend fun createCustomer(request: CreateCustomerRequest): ApiResult<Customer> = safeApiCall { api.createCustomer(request) }
    suspend fun updateCustomer(id: String, request: CreateCustomerRequest): ApiResult<Customer> = safeApiCall { api.updateCustomer(id, request) }
    suspend fun deleteCustomer(id: String): ApiResult<Unit> = safeActionCall { api.deleteCustomer(id) }
}
