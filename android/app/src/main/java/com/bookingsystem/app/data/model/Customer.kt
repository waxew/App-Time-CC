package com.bookingsystem.app.data.model

import com.google.gson.annotations.SerializedName

data class Customer(
    val id: String,
    @SerializedName("full_name") val fullName: String,
    val phone: String,
    val birthday: String? = null,
    val notes: String? = null,
    val tags: List<String>? = null,
    @SerializedName("visit_count") val visitCount: Int? = null
)

data class CreateCustomerRequest(
    val fullName: String,
    val phone: String,
    val birthday: String? = null,
    val notes: String? = null,
    val tags: List<String>? = null
)
