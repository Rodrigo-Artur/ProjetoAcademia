package com.example.projetoacademia.model

data class Aluno(
    val nome: String,
    val cpf: String,
    val telefone: String,
    val dataNascimento: String,
    val email: String,
    val objetivo: String,
    val ativo: Boolean,
    val fotoUri: String = ""
)
