package com.bookingsystem.app.data.remote

import com.bookingsystem.app.data.model.ApiResponse
import com.bookingsystem.app.data.model.Appointment
import com.bookingsystem.app.data.model.AuthResult
import com.bookingsystem.app.data.model.CreateAppointmentRequest
import com.bookingsystem.app.data.model.CreateCustomerRequest
import com.bookingsystem.app.data.model.CreateServiceRequest
import com.bookingsystem.app.data.model.CreateStaffRequest
import com.bookingsystem.app.data.model.Customer
import com.bookingsystem.app.data.model.LoginRequest
import com.bookingsystem.app.data.model.RegisterRequest
import com.bookingsystem.app.data.model.Service
import com.bookingsystem.app.data.model.Staff
import com.bookingsystem.app.data.model.UpdateAppointmentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/auth/register")
    suspend fun register(@Body body: RegisterRequest): Response<ApiResponse<AuthResult>>

    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequest): Response<ApiResponse<AuthResult>>

    @GET("api/appointments")
    suspend fun getAppointments(
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("status") status: String?
    ): Response<ApiResponse<List<Appointment>>>

    @POST("api/appointments")
    suspend fun createAppointment(@Body body: CreateAppointmentRequest): Response<ApiResponse<Appointment>>

    @PATCH("api/appointments/{id}")
    suspend fun updateAppointment(@Path("id") id: String, @Body body: UpdateAppointmentRequest): Response<ApiResponse<Appointment>>

    @DELETE("api/appointments/{id}")
    suspend fun deleteAppointment(@Path("id") id: String): Response<ApiResponse<Unit>>

    @GET("api/customers")
    suspend fun getCustomers(@Query("search") search: String?): Response<ApiResponse<List<Customer>>>

    @POST("api/customers")
    suspend fun createCustomer(@Body body: CreateCustomerRequest): Response<ApiResponse<Customer>>

    @PATCH("api/customers/{id}")
    suspend fun updateCustomer(@Path("id") id: String, @Body body: CreateCustomerRequest): Response<ApiResponse<Customer>>

    @DELETE("api/customers/{id}")
    suspend fun deleteCustomer(@Path("id") id: String): Response<ApiResponse<Unit>>

    @GET("api/services")
    suspend fun getServices(): Response<ApiResponse<List<Service>>>

    @POST("api/services")
    suspend fun createService(@Body body: CreateServiceRequest): Response<ApiResponse<Service>>

    @PATCH("api/services/{id}")
    suspend fun updateService(@Path("id") id: String, @Body body: CreateServiceRequest): Response<ApiResponse<Service>>

    @DELETE("api/services/{id}")
    suspend fun deleteService(@Path("id") id: String): Response<ApiResponse<Unit>>

    @GET("api/staff")
    suspend fun getStaff(): Response<ApiResponse<List<Staff>>>

    @POST("api/staff")
    suspend fun createStaff(@Body body: CreateStaffRequest): Response<ApiResponse<Staff>>

    @DELETE("api/staff/{id}")
    suspend fun deleteStaff(@Path("id") id: String): Response<ApiResponse<Unit>>
}
