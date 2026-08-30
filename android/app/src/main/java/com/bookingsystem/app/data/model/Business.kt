package com.bookingsystem.app.data.model

import com.google.gson.annotations.SerializedName

data class Business(
    val id: String,
    val name: String,
    @SerializedName("owner_name") val ownerName: String? = null,
    val phone: String,
    @SerializedName("business_type") val businessType: String? = null
)

data class AuthResult(val business: Business, val token: String)

data class RegisterRequest(
    val name: String,
    val ownerName: String? = null,
    val phone: String,
    val password: String,
    val businessType: String? = null
)

data class LoginRequest(val phone: String, val password: String)
