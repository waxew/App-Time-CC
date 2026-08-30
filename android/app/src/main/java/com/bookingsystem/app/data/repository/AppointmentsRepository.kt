package com.bookingsystem.app.data.repository

import android.content.Context
import com.bookingsystem.app.data.model.Appointment
import com.bookingsystem.app.data.model.CreateAppointmentRequest
import com.bookingsystem.app.data.model.UpdateAppointmentRequest
import com.bookingsystem.app.data.remote.RetrofitClient

class AppointmentsRepository(context: Context) {
    private val api = RetrofitClient.getApiService(context)
    suspend fun getAppointments(from: String? = null, to: String? = null, status: String? = null): ApiResult<List<Appointment>> = safeApiCall { api.getAppointments(from, to, status) }
    suspend fun createAppointment(request: CreateAppointmentRequest): ApiResult<Appointment> = safeApiCall { api.createAppointment(request) }
    suspend fun updateAppointment(id: String, request: UpdateAppointmentRequest): ApiResult<Appointment> = safeApiCall { api.updateAppointment(id, request) }
    suspend fun deleteAppointment(id: String): ApiResult<Unit> = safeActionCall { api.deleteAppointment(id) }
}
