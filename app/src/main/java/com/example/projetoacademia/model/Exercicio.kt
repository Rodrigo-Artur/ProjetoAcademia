package com.example.projetoacademia.model

data class Exercicio(
    val nome: String,
    val categoria: String,
    val descricao: String,
    val fotoUri: String = "",
    val videoUri: String = ""
)
