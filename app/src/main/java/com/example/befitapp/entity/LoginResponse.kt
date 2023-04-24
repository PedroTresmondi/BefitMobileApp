package com.example.befitapp.entity

data class LoginResponse(
    val id: Int,
    val nome: String,
    val email: String,
    val personId: String,
    val xp: Int,
    val logado: Boolean
)