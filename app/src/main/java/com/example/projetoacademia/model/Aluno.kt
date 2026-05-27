package com.example.projetoacademia.model

data class Aluno(
    //indentificação do aluno
    val nome: String,
    val cpf: String,
    val telefone: String,
    val dataNascimento: String,
    val email: String,
    val objetivo: String,
    //diz se o aluno está ativo ou não
    val ativo: Boolean
)