package com.rubmar.goodgym.auth

import com.rubmar.goodgym.network.ApiClient
import com.rubmar.goodgym.network.RegisterRequest

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult<User>
    suspend fun register(nombre: String, apellido: String, edad: Int, email: String, password: String, plan: String): AuthResult<Unit>
}

class DefaultAuthRepository : AuthRepository {
    private val apiService = ApiClient.instance
    private val loggedInUsers = mutableMapOf<String, String>()

    override suspend fun login(email: String, password: String): AuthResult<User> {
        if (loggedInUsers.containsKey(email) && loggedInUsers[email] == password) {
            val mockUser = User(
                id = email.hashCode().toString(),
                email = email,
                nombre = "Usuario",
                apellido = "Registrado",
                edad = 25
            )
            return AuthResult.Success(mockUser)
        } else {
            return AuthResult.Error("Correo o contraseña incorrectos")
        }
    }

    override suspend fun register(nombre: String, apellido: String, edad: Int, email: String, password: String, plan: String): AuthResult<Unit> {
        if (nombre.isBlank() || apellido.isBlank() || email.isBlank() || password.isBlank() || plan.isBlank()) {
            return AuthResult.Error("Todos los campos son obligatorios")
        }
        if (edad < 16) {
            return AuthResult.Error("Debes tener al menos 16 años para registrarte")
        }

        return try {
            val request = RegisterRequest(nombre, apellido, email, edad, password, plan)
            val response = apiService.registerUser(request)

            if (response.isSuccessful) {
                loggedInUsers[email] = password
                AuthResult.Success(Unit)
            } else {
                AuthResult.Error("Error del servidor: ${response.code()}")
            }
        } catch (e: Exception) {
            AuthResult.Error("Error de red: ${e.message}")
        }
    }
}
