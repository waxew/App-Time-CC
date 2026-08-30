package com.bookingsystem.app.data.remote

import android.content.Context
import com.bookingsystem.app.data.local.TokenManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:4000/"

    @Volatile
    private var retrofit: Retrofit? = null

    fun getApiService(context: Context): ApiService {
        return buildRetrofit(context.applicationContext).create(ApiService::class.java)
    }

    @Synchronized
    private fun buildRetrofit(context: Context): Retrofit {
        retrofit?.let { return it }
        val tokenManager = TokenManager(context)
        val authInterceptor = Interceptor { chain ->
            val token = runBlocking { tokenManager.getToken() }
            val requestBuilder = chain.request().newBuilder()
            if (!token.isNullOrBlank()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
        val gson: Gson = GsonBuilder().setLenient().create()
        val built = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit = built
        return built
    }
}
