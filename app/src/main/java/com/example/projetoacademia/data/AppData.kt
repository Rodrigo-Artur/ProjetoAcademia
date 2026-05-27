package com.example.projetoacademia.data

import androidx.compose.runtime.mutableStateListOf
import com.example.projetoacademia.model.Aluno
import com.example.projetoacademia.model.Exercicio
import com.example.projetoacademia.model.ExercicioDoTreino
import com.example.projetoacademia.model.Pagamento
import com.example.projetoacademia.model.Plano
import com.example.projetoacademia.model.Treinador
import com.example.projetoacademia.model.Treino

object AppData {
    val alunos = mutableStateListOf<Aluno>()
    val planos = mutableStateListOf<Plano>()
    val treinos = mutableStateListOf<Treino>()
    val pagamentos = mutableStateListOf<Pagamento>()
    val exercicios = mutableStateListOf<Exercicio>()
    val treinadores = mutableStateListOf<Treinador>()

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

        if (treinadores.isEmpty()) {
            treinadores.addAll(
                listOf(
                    Treinador("Marina Rocha", "Hipertrofia", "1799999-1010", "marina@academia.com", true),
                    Treinador("Rafael Nunes", "Emagrecimento", "1799999-2020", "rafael@academia.com", true),
                    Treinador("Patrícia Gomes", "Força e mobilidade", "1799999-3030", "patricia@academia.com", true)
                )
            )
        }

        if (alunos.isEmpty()) {
            alunos.addAll(
                listOf(
                    Aluno("Ana Souza", "111.111.111-11", "1799999-1111", "10/05/2000", "ana@email.com", "Hipertrofia", true, plano = "Mensal", treinador = "Marina Rocha"),
                    Aluno("Bruno Lima", "222.222.222-22", "1799999-2222", "20/03/1998", "bruno@email.com", "Emagrecimento", true, plano = "Premium", treinador = "Rafael Nunes"),
                    Aluno("Carla Mendes", "333.333.333-33", "1799999-3333", "14/08/2001", "carla@email.com", "Resistência", false, plano = "Trimestral", treinador = "Patrícia Gomes"),
                    Aluno("Diego Alves", "444.444.444-44", "1799999-4444", "01/11/1997", "diego@email.com", "Força", true, plano = "Premium", treinador = "Patrícia Gomes"),
                    Aluno("Elaine Costa", "555.555.555-55", "1799999-5555", "07/02/2002", "elaine@email.com", "Condicionamento", true, plano = "Mensal", treinador = "Rafael Nunes")
                )
            )
        }

        if (exercicios.isEmpty()) {
            exercicios.addAll(
                listOf(
                    Exercicio("Supino reto", "Peito", "Exercício base para peitoral com barra ou halteres"),
                    Exercicio("Crucifixo inclinado", "Peito", "Isolamento para peitoral superior com halteres"),
                    Exercicio("Puxada frontal", "Costas", "Exercício para dorsais usando polia alta"),
                    Exercicio("Remada baixa", "Costas", "Remada sentada para dorsais e romboides"),
                    Exercicio("Agachamento livre", "Pernas", "Movimento composto para quadríceps, glúteos e core"),
                    Exercicio("Leg press", "Pernas", "Exercício de força para pernas com máquina"),
                    Exercicio("Rosca direta", "Braços", "Exercício clássico para bíceps"),
                    Exercicio("Tríceps corda", "Braços", "Extensão de tríceps na polia com corda"),
                    Exercicio("Desenvolvimento militar", "Ombros", "Press vertical para deltoides"),
                    Exercicio("Elevação lateral", "Ombros", "Isolamento para deltoide lateral"),
                    Exercicio("Prancha", "Abdômen", "Isometria para fortalecimento do core"),
                    Exercicio("Abdominal infra", "Abdômen", "Fortalecimento da região inferior do abdômen"),
                    Exercicio("Esteira intervalada", "Cardio", "Corrida alternando intensidade alta e baixa"),
                    Exercicio("Bicicleta ergométrica", "Cardio", "Condicionamento cardiovascular de baixo impacto"),
                    Exercicio("Alongamento de quadril", "Mobilidade", "Mobilidade para quadril e cadeia posterior")
                )
            )
        }

        if (treinos.isEmpty()) {
            treinos.addAll(
                listOf(
                    Treino(
                        "Ana Souza", "Hipertrofia A", "Peito", "", "4", "10-12", "Aumentar carga progressivamente",
                        listOf("Supino reto", "Crucifixo inclinado"),
                        listOf(
                            ExercicioDoTreino("Supino reto", "40 kg", "4x10"),
                            ExercicioDoTreino("Crucifixo inclinado", "12 kg", "3x12")
                        )
                    ),
                    Treino(
                        "Bruno Lima", "Condicionamento", "Cardio", "", "5", "3 min", "Controlar frequência cardíaca",
                        listOf("Esteira intervalada", "Bicicleta ergométrica"),
                        listOf(
                            ExercicioDoTreino("Esteira intervalada", "Vel. 9", "5x3 min"),
                            ExercicioDoTreino("Bicicleta ergométrica", "Carga 6", "20 min")
                        )
                    ),
                    Treino(
                        "Diego Alves", "Força", "Pernas", "", "5", "5", "Priorizar execução",
                        listOf("Agachamento livre", "Leg press"),
                        listOf(
                            ExercicioDoTreino("Agachamento livre", "80 kg", "5x5"),
                            ExercicioDoTreino("Leg press", "160 kg", "4x8")
                        )
                    ),
                    Treino(
                        "Elaine Costa", "Core e mobilidade", "Abdômen", "", "3", "30 s", "Focar postura e respiração",
                        listOf("Prancha", "Alongamento de quadril"),
                        listOf(
                            ExercicioDoTreino("Prancha", "Peso corporal", "3x30 s"),
                            ExercicioDoTreino("Alongamento de quadril", "Peso corporal", "3x40 s")
                        )
                    )
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
