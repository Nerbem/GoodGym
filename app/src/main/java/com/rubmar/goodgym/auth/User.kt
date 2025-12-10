package com.rubmar.goodgym.auth

data class User(
    val id: String,
    val email: String,
    val nombre: String,
    val apellido: String,
    val edad: Int
)
