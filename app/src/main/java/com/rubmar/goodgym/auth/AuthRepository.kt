package com.rubmar.goodgym.auth

import com.rubmar.goodgym.network.ApiClient
import com.rubmar.goodgym.network.RegisterDTO
import com.rubmar.goodgym.network.UserDTO

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult<User>
    suspend fun register(nombre: String, apellido: String, edad: Int, email: String, password: String, plan: String): AuthResult<Unit>
    suspend fun getUsers(): AuthResult<List<User>>
    suspend fun deleteUser(userId: String): AuthResult<Unit>
    suspend fun updateUser(userId: String, nombre: String, apellido: String, edad: Int, email: String, password: String, plan: String): AuthResult<Unit>
}

class DefaultAuthRepository : AuthRepository {
    private val apiService = ApiClient.instance

    override suspend fun login(email: String, password: String): AuthResult<User> {
        return try {
            val allUsers = apiService.getUsers()
            val foundUser = allUsers.find { it.email == email && it.password == password }

            if (foundUser != null) {
                AuthResult.Success(foundUser.toDomainModel())
            } else {
                AuthResult.Error("Usuario o contraseña incorrectos")
            }
        } catch (e: Exception) {
            AuthResult.Error("Error de red al iniciar sesión: ${e.message}")
        }
    }

    override suspend fun register(nombre: String, apellido: String, edad: Int, email: String, password: String, plan: String): AuthResult<Unit> {
        return try {
            val request = RegisterDTO(nombre, apellido, email, edad, password, plan)
            val response = apiService.registerUser(request)
            if (response.isSuccessful) {
                AuthResult.Success(Unit)
            } else {
                AuthResult.Error("Error del servidor: ${response.code()}")
            }
        } catch (e: Exception) {
            AuthResult.Error("Error de red: ${e.message}")
        }
    }

    override suspend fun getUsers(): AuthResult<List<User>> {
        return try {
            val userDTOs = apiService.getUsers()
            val users = userDTOs.map { it.toDomainModel() }
            AuthResult.Success(users)
        } catch (e: Exception) {
            AuthResult.Error("Error de red al obtener usuarios: ${e.message}")
        }
    }

    override suspend fun deleteUser(userId: String): AuthResult<Unit> {
        return try {
            val response = apiService.deleteUser(userId)
            if (response.isSuccessful) {
                AuthResult.Success(Unit)
            } else {
                AuthResult.Error("Error del servidor al eliminar: ${response.code()}")
            }
        } catch (e: Exception) {
            AuthResult.Error("Error de red al eliminar: ${e.message}")
        }
    }

    override suspend fun updateUser(userId: String, nombre: String, apellido: String, edad: Int, email: String, password: String, plan: String): AuthResult<Unit> {
        return try {
            val request = RegisterDTO(nombre, apellido, email, edad, password, plan)
            val response = apiService.updateUser(userId, request)
            if (response.isSuccessful) {
                AuthResult.Success(Unit)
            } else {
                AuthResult.Error("Error del servidor al actualizar: ${response.code()}")
            }
        } catch (e: Exception) {
            AuthResult.Error("Error de red al actualizar: ${e.message}")
        }
    }
}

private fun UserDTO.toDomainModel(): User {
    return User(
        id = this.id.toString(),
        email = this.email,
        nombre = this.nombre,
        apellido = this.apellido,
        edad = this.edad
    )
}
