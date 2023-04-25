package com.example.befitapp.entity

data class Ingrediente(
    val id: Int,
    val nome: String,
    val porcao: Int,
    val proteina: Double,
    val lipidio: Double,
    val carboidrato: Double,
    val sodio: Double,
    val caloria: Double
)
