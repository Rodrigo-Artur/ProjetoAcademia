package com.example.projetoacademia.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

object AppCreateActions {
    var alunos by mutableIntStateOf(0)
    var planos by mutableIntStateOf(0)
    var exercicios by mutableIntStateOf(0)
    var treinadores by mutableIntStateOf(0)
    var treinos by mutableIntStateOf(0)
    var pagamentos by mutableIntStateOf(0)

    fun solicitarCriacao(route: String?) {
        when (route) {
            Routes.ALUNOS -> alunos++
            Routes.PLANOS -> planos++
            Routes.EXERCICIOS -> exercicios++
            Routes.TREINADORES -> treinadores++
            Routes.TREINOS -> treinos++
            Routes.PAGAMENTOS -> pagamentos++
        }
    }
}
