package com.example.projetoacademia.model

data class Treino(
    val aluno: String,
    val tipoTreino: String,
    val grupoMuscular: String,
    val exercicios: String,
    val series: String,
    val repeticoes: String,
    val observacoes: String,
    val exerciciosSelecionados: List<String> = emptyList(),
    val detalhesExercicios: List<ExercicioDoTreino> = emptyList()
)
