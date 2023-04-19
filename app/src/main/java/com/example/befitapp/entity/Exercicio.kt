package com.example.befitapp.entity

import java.time.LocalTime

data class Exercicio(
    val idExercicio: Int,
    val nome: String,
    val descricao: String,
    val imagem: String,
    val quantidade: Int,
    val tempo: String,
    val repeticao: Int
)
