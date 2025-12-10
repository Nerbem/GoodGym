package com.rubmar.goodgym.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class RegisterRequest(
    val nombre: String,
    val apellido: String,
    val correo_electronico: String,
    val edad: Int,
    val contrasenya: String,
    val plan: String // Se añade el plan
)

@Serializable
data class LoginRequest(
    val correo_electronico: String,
    val contrasenya: String
)

@Serializable
data class UserResponse(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val correo_electronico: String,
    val edad: Int
)

interface ApiService {
    @POST("/user")
    suspend fun registerUser(@Body request: RegisterRequest): Response<Unit>
}

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private val json = Json { ignoreUnknownKeys = true }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        retrofit.create(ApiService::class.java)
    }
}