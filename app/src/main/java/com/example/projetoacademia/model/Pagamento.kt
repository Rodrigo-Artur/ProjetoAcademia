package com.example.projetoacademia.model

data class Pagamento(
    val aluno: String,
    val plano: String,
    val data: String,
    val valor: Double,
    val formaPagamento: String,
    val situacao: String
)