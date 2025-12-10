package com.rubmar.goodgym.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

@Serializable
data class RegisterDTO(
    val nombre: String,
    val apellido: String,
    @SerialName("correo_electronico") val email: String,
    val edad: Int,
    @SerialName("contrasenya") val password: String,
    val plan: String
)

@Serializable
data class UserDTO(
    val id: Int,
    val nombre: String,
    val apellido: String,
    @SerialName("correo_electronico") val email: String,
    val edad: Int,
    @SerialName("contrasenya") val password: String,
    val plan: String? = null
)

interface ApiService {
    @POST("user")
    suspend fun registerUser(@Body request: RegisterDTO): Response<Unit>

    @GET("user")
    suspend fun getUsers(): List<UserDTO>

    @DELETE("user/{id}")
    suspend fun deleteUser(@Path("id") userId: String): Response<Unit>

    @PUT("user/{id}")
    suspend fun updateUser(@Path("id") userId: String, @Body request: RegisterDTO): Response<Unit>
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