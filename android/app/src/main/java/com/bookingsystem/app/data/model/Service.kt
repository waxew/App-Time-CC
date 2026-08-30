package com.bookingsystem.app.data.model

import com.google.gson.annotations.SerializedName

data class Service(
    val id: String,
    val name: String,
    @SerializedName("duration_minutes") val durationMinutes: Int,
    val price: Double? = null,
    val description: String? = null,
    @SerializedName("is_active") val isActive: Boolean? = null
)

data class CreateServiceRequest(
    val name: String,
    val durationMinutes: Int = 30,
    val price: Double? = null,
    val description: String? = null
)
