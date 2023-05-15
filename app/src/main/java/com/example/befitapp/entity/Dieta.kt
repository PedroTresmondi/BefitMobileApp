package com.example.befitapp.entity

data class Dieta(
    val id: Int,
    val nome: String,
    val descricao: String,
    val imagem: String,
    var favoritado: Boolean = false
)
