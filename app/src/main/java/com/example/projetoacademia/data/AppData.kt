package com.example.projetoacademia.data

import androidx.compose.runtime.mutableStateListOf
import com.example.projetoacademia.model.Aluno
import com.example.projetoacademia.model.Pagamento
import com.example.projetoacademia.model.Plano
import com.example.projetoacademia.model.Treino

object AppData {
    val alunos = mutableStateListOf<Aluno>()
    val planos = mutableStateListOf<Plano>()
    val treinos = mutableStateListOf<Treino>()
    val pagamentos = mutableStateListOf<Pagamento>()
}