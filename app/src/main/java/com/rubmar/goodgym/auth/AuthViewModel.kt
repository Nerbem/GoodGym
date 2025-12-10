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

    private val _userListState = MutableStateFlow<AuthResult<List<User>>>(AuthResult.Idle)
    val userListState: StateFlow<AuthResult<List<User>>> = _userListState

    private val _deleteUserState = MutableStateFlow<AuthResult<Unit>>(AuthResult.Idle)
    val deleteUserState: StateFlow<AuthResult<Unit>> = _deleteUserState

    private val _updateUserState = MutableStateFlow<AuthResult<Unit>>(AuthResult.Idle)
    val updateUserState: StateFlow<AuthResult<Unit>> = _updateUserState

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

    fun getUsers() {
        viewModelScope.launch {
            _userListState.value = AuthResult.Loading
            val result = repo.getUsers()
            _userListState.value = result
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            _deleteUserState.value = AuthResult.Loading
            val result = repo.deleteUser(userId)
            _deleteUserState.value = result
        }
    }

    fun updateUser(userId: String, nombre: String, apellido: String, edad: String, email: String, password: String, plan: String) {
        viewModelScope.launch {
            _updateUserState.value = AuthResult.Loading
            val ageAsInt = edad.toIntOrNull() ?: 0
            val result = repo.updateUser(userId, nombre, apellido, ageAsInt, email, password, plan)
            _updateUserState.value = result
        }
    }

    fun resetAuthState() {
        _authState.value = AuthResult.Idle
    }

    fun resetRegistrationState() {
        _registrationState.value = AuthResult.Idle
    }

    fun resetDeleteUserState() {
        _deleteUserState.value = AuthResult.Idle
    }

    fun resetUpdateUserState() {
        _updateUserState.value = AuthResult.Idle
    }
}