package com.bookingsystem.app.data.model

import com.google.gson.annotations.SerializedName

data class Appointment(
    val id: String,
    @SerializedName("customer_id") val customerId: String? = null,
    @SerializedName("service_id") val serviceId: String? = null,
    @SerializedName("staff_id") val staffId: String? = null,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String? = null,
    val status: String,
    val notes: String? = null,
    @SerializedName("customer_name") val customerName: String? = null,
    @SerializedName("customer_phone") val customerPhone: String? = null,
    @SerializedName("service_name") val serviceName: String? = null,
    @SerializedName("duration_minutes") val durationMinutes: Int? = null,
    @SerializedName("staff_name") val staffName: String? = null
)

data class CreateAppointmentRequest(
    val customerId: String? = null,
    val serviceId: String,
    val staffId: String? = null,
    val startTime: String,
    val notes: String? = null,
    val createdVia: String = "admin"
)

data class UpdateAppointmentRequest(
    val status: String? = null,
    val startTime: String? = null,
    val notes: String? = null
)
