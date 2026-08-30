package com.bookingsystem.app.data.model

import com.google.gson.annotations.SerializedName

data class Staff(
    val id: String,
    @SerializedName("full_name") val fullName: String,
    val phone: String? = null,
    val role: String? = null,
    @SerializedName("is_active") val isActive: Boolean? = null
)

data class CreateStaffRequest(
    val fullName: String,
    val phone: String? = null,
    val role: String = "staff",
    val password: String? = null
)
