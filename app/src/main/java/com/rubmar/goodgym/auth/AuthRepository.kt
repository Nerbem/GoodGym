package com.rubmar.goodgym.auth

import kotlinx.coroutines.delay

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult<User>
    suspend fun register(email: String, password: String, confirmPassword: String, nombre: String, apellido: String, edad: Int): AuthResult<Unit>
}

class DefaultAuthRepository : AuthRepository {
    // Simulamos una base de datos en memoria
    private val users = mutableMapOf<String, User>()
    private val passwords = mutableMapOf<String, String>()

    init {
        // Añadimos un usuario de prueba por defecto para poder iniciar sesión sin registrarse
        val defaultEmail = "test@example.com"
        users[defaultEmail] = User(
            id = "u_${defaultEmail.hashCode()}",
            email = defaultEmail,
            nombre = "Usuario",
            apellido = "Prueba",
            edad = 25
        )
        passwords[defaultEmail] = "password123"
    }

    override suspend fun login(email: String, password: String): AuthResult<User> {
        if (email.isBlank() || password.isBlank()) {
            return AuthResult.Error("Credenciales vacías")
        }
        delay(700) // simula red

        val user = users[email]
        val storedPassword = passwords[email]

        return if (user != null && storedPassword == password) {
            AuthResult.Success(user)
        } else {
            AuthResult.Error("Correo o contraseña incorrectos")
        }
    }

    override suspend fun register(email: String, password: String, confirmPassword: String, nombre: String, apellido: String, edad: Int): AuthResult<Unit> {
        if (nombre.isBlank() || apellido.isBlank() || email.isBlank() || password.isBlank()) {
            return AuthResult.Error("Todos los campos son obligatorios")
        }
        if (password != confirmPassword) {
            return AuthResult.Error("Las contraseñas no coinciden")
        }
        if (edad < 16) {
            return AuthResult.Error("Debes tener al menos 16 años para registrarte")
        }
        if (users.containsKey(email)) {
            return AuthResult.Error("El correo electrónico ya está en uso")
        }

        delay(700) // simula red

        // Guardamos el nuevo usuario en nuestra "base de datos"
        val newUser = User(
            id = "u_${email.hashCode()}",
            email = email,
            nombre = nombre,
            apellido = apellido,
            edad = edad
        )
        users[email] = newUser
        passwords[email] = password

        return AuthResult.Success(Unit)
    }
}
