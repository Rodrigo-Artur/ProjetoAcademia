package com.example.projetoacademia.model

data class Treinador(
    val nome: String,
    val especialidade: String,
    val telefone: String,
    val email: String,
    val ativo: Boolean = true
)
