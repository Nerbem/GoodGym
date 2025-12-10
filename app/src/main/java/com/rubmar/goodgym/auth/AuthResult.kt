package com.rubmar.goodgym.auth

sealed class AuthResult<out T> {
    object Idle : AuthResult<Nothing>()
    object Loading : AuthResult<Nothing>()
    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val message: String?) : AuthResult<Nothing>()
}
