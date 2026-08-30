package com.bookingsystem.app.data.repository

import com.bookingsystem.app.data.model.ApiResponse
import retrofit2.Response

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}

suspend fun <T> safeApiCall(call: suspend () -> Response<ApiResponse<T>>): ApiResult<T> {
    return try {
        val response = call()
        val body = response.body()
        val data = body?.data
        if (response.isSuccessful && body?.success == true && data != null) {
            ApiResult.Success(data)
        } else {
            ApiResult.Error(body?.message ?: "خطایی در ارتباط با سرور رخ داد")
        }
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "خطا در اتصال به اینترنت")
    }
}

suspend fun safeActionCall(call: suspend () -> Response<ApiResponse<Unit>>): ApiResult<Unit> {
    return try {
        val response = call()
        val body = response.body()
        if (response.isSuccessful && body?.success == true) {
            ApiResult.Success(Unit)
        } else {
            ApiResult.Error(body?.message ?: "خطایی در ارتباط با سرور رخ داد")
        }
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "خطا در اتصال به اینترنت")
    }
}
