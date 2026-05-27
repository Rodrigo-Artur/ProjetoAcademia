package com.example.projetoacademia.data

import androidx.compose.runtime.mutableStateListOf
import com.example.projetoacademia.model.Aluno
import com.example.projetoacademia.model.Exercicio
import com.example.projetoacademia.model.Pagamento
import com.example.projetoacademia.model.Plano
import com.example.projetoacademia.model.Treino

object AppData {
    val alunos = mutableStateListOf<Aluno>()
    val planos = mutableStateListOf<Plano>()
    val treinos = mutableStateListOf<Treino>()
    val pagamentos = mutableStateListOf<Pagamento>()
    val exercicios = mutableStateListOf<Exercicio>()

    init {
        carregarDadosDeExemplo()
    }

    private fun carregarDadosDeExemplo() {
        if (planos.isEmpty()) {
            planos.addAll(
                listOf(
                    Plano("Mensal", 89.90, "1 mês", "Acesso livre à musculação"),
                    Plano("Trimestral", 239.90, "3 meses", "Plano econômico para alunos frequentes"),
                    Plano("Premium", 149.90, "1 mês", "Musculação, avaliação e acompanhamento")
                )
            )
        }

        if (alunos.isEmpty()) {
            alunos.addAll(
                listOf(
                    Aluno("Ana Souza", "111.111.111-11", "1799999-1111", "10/05/2000", "ana@email.com", "Hipertrofia", true, plano = "Mensal"),
                    Aluno("Bruno Lima", "222.222.222-22", "1799999-2222", "20/03/1998", "bruno@email.com", "Emagrecimento", true, plano = "Premium"),
                    Aluno("Carla Mendes", "333.333.333-33", "1799999-3333", "14/08/2001", "carla@email.com", "Resistência", false, plano = "Trimestral"),
                    Aluno("Diego Alves", "444.444.444-44", "1799999-4444", "01/11/1997", "diego@email.com", "Força", true, plano = "Premium"),
                    Aluno("Elaine Costa", "555.555.555-55", "1799999-5555", "07/02/2002", "elaine@email.com", "Condicionamento", true, plano = "Mensal")
                )
            )
        }

        if (exercicios.isEmpty()) {
            exercicios.addAll(
                listOf(
                    Exercicio("Supino reto", "Peito", "Exercício base para peitoral com barra ou halteres"),
                    Exercicio("Agachamento livre", "Pernas", "Movimento composto para quadríceps, glúteos e core"),
                    Exercicio("Puxada frontal", "Costas", "Exercício para dorsais usando polia alta"),
                    Exercicio("Rosca direta", "Braços", "Exercício clássico para bíceps"),
                    Exercicio("Prancha", "Abdômen", "Isometria para fortalecimento do core")
                )
            )
        }

        if (treinos.isEmpty()) {
            treinos.addAll(
                listOf(
                    Treino("Ana Souza", "Hipertrofia A", "Peito", "", "4", "10-12", "Aumentar carga progressivamente", listOf("Supino reto")),
                    Treino("Bruno Lima", "Condicionamento", "Cardio", "Esteira intervalada", "5", "3 min", "Controlar frequência cardíaca", emptyList()),
                    Treino("Diego Alves", "Força", "Pernas", "", "5", "5", "Priorizar execução", listOf("Agachamento livre"))
                )
            )
        }

        if (pagamentos.isEmpty()) {
            pagamentos.addAll(
                listOf(
                    Pagamento("Ana Souza", "Mensal", "01/05/2026", 89.90, "Pix", "Pago"),
                    Pagamento("Bruno Lima", "Premium", "02/05/2026", 149.90, "Cartão", "Pago"),
                    Pagamento("Carla Mendes", "Trimestral", "03/05/2026", 239.90, "Dinheiro", "Pendente"),
                    Pagamento("Diego Alves", "Premium", "04/05/2026", 149.90, "Pix", "Atrasado"),
                    Pagamento("Elaine Costa", "Mensal", "05/05/2026", 89.90, "Cartão", "Pago")
                )
            )
        }
    }
}
