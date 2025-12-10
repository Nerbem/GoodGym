package com.rubmar.goodgym.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: AuthRepository = DefaultAuthRepository()
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthResult<User>>(AuthResult.Idle)
    val authState: StateFlow<AuthResult<User>> = _authState

    private val _registrationState = MutableStateFlow<AuthResult<Unit>>(AuthResult.Idle)
    val registrationState: StateFlow<AuthResult<Unit>> = _registrationState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthResult.Loading
            val result = repo.login(email, password)
            _authState.value = result
        }
    }

    fun register(nombre: String, apellido: String, edad: String, email: String, password: String, plan: String) {
        viewModelScope.launch {
            _registrationState.value = AuthResult.Loading
            val ageAsInt = edad.toIntOrNull() ?: 0
            val result = repo.register(nombre, apellido, ageAsInt, email, password, plan)
            _registrationState.value = result
        }
    }

    fun resetAuthState() {
        _authState.value = AuthResult.Idle
    }

    fun resetRegistrationState() {
        _registrationState.value = AuthResult.Idle
    }
}