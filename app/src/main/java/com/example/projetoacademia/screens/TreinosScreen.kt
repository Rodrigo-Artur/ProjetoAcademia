package com.example.projetoacademia.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projetoacademia.data.AppData
import com.example.projetoacademia.model.Treino

@Composable
fun TreinosScreen(onVoltarClick: () -> Unit) {
    val alunos = AppData.alunos
    val treinos = AppData.treinos

    var alunoSelecionado by remember { mutableStateOf("") }
    var tipoTreino by remember { mutableStateOf("") }
    var grupoMuscular by remember { mutableStateOf("") }
    var exercicios by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var repeticoes by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }

    var mensagemErro by remember { mutableStateOf("") }
    var indiceEditando by remember { mutableStateOf<Int?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    fun limparFormulario() {
        alunoSelecionado = ""
        tipoTreino = ""
        grupoMuscular = ""
        exercicios = ""
        series = ""
        repeticoes = ""
        observacoes = ""
        mensagemErro = ""
        indiceEditando = null
        mostrarFormulario = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = if (indiceEditando == null) "Treinos" else "Editar Treino",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Cadastre, edite e remova treinos vinculados aos alunos.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        if (!mostrarFormulario && indiceEditando == null) {
            Button(
                onClick = { mostrarFormulario = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cadastrar treino")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (mostrarFormulario || indiceEditando != null) {
            Text(text = "Aluno", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            if (alunos.isEmpty()) {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        text = "Cadastre pelo menos um aluno antes de criar um treino.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    alunos.forEach { aluno ->
                        FilterChip(
                            selected = alunoSelecionado == aluno.nome,
                            onClick = { alunoSelecionado = aluno.nome },
                            label = { Text(text = aluno.nome) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = tipoTreino,
                onValueChange = { tipoTreino = it },
                label = { Text(text = "Tipo de treino") },
                placeholder = { Text(text = "Ex: Hipertrofia") },
                modifier = Modifier.fillMaxWidth(),
                isError = mensagemErro.contains("tipo", ignoreCase = true)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = grupoMuscular,
                onValueChange = { grupoMuscular = it },
                label = { Text(text = "Grupo muscular") },
                placeholder = { Text(text = "Ex: Peito e tríceps") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = exercicios,
                onValueChange = { exercicios = it },
                label = { Text(text = "Exercícios") },
                placeholder = { Text(text = "Ex: Supino, crucifixo, tríceps corda") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = series,
                    onValueChange = { series = it },
                    label = { Text(text = "Séries") },
                    placeholder = { Text(text = "Ex: 4") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = repeticoes,
                    onValueChange = { repeticoes = it },
                    label = { Text(text = "Repetições") },
                    placeholder = { Text(text = "Ex: 12") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = observacoes,
                onValueChange = { observacoes = it },
                label = { Text(text = "Observações") },
                placeholder = { Text(text = "Ex: Aumentar carga a cada semana") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            if (mensagemErro.isNotBlank()) {
                Text(
                    text = mensagemErro,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (alunos.isEmpty()) {
                        mensagemErro = "É necessário cadastrar um aluno antes de cadastrar treinos"
                        return@Button
                    }

                    if (alunoSelecionado.isBlank()) {
                        mensagemErro = "Selecione um aluno"
                        return@Button
                    }

                    if (tipoTreino.isBlank()) {
                        mensagemErro = "Informe o tipo de treino"
                        return@Button
                    }

                    val treino = Treino(
                        aluno = alunoSelecionado,
                        tipoTreino = tipoTreino,
                        grupoMuscular = grupoMuscular,
                        exercicios = exercicios,
                        series = series,
                        repeticoes = repeticoes,
                        observacoes = observacoes
                    )

                    val index = indiceEditando

                    if (index == null) {
                        treinos.add(treino)
                    } else {
                        treinos[index] = treino
                    }

                    limparFormulario()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (indiceEditando == null) "Salvar treino" else "Atualizar treino")
            }

            OutlinedButton(
                onClick = { limparFormulario() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Cancelar")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Text(text = "Treinos cadastrados", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        if (treinos.isEmpty()) {
            Text(text = "Nenhum treino cadastrado ainda.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                treinos.forEachIndexed { index, treino ->
                    TreinoCard(
                        treino = treino,
                        onEditarClick = {
                            alunoSelecionado = treino.aluno
                            tipoTreino = treino.tipoTreino
                            grupoMuscular = treino.grupoMuscular
                            exercicios = treino.exercicios
                            series = treino.series
                            repeticoes = treino.repeticoes
                            observacoes = treino.observacoes
                            mensagemErro = ""
                            indiceEditando = index
                            mostrarFormulario = true
                        },
                        onExcluirClick = {
                            treinos.removeAt(index)
                            limparFormulario()
                        }
                    )
                }
            }
        }

        TextButton(
            onClick = onVoltarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar")
        }
    }
}

@Composable
fun TreinoCard(
    treino: Treino,
    onEditarClick: () -> Unit,
    onExcluirClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = treino.tipoTreino, style = MaterialTheme.typography.titleMedium)
            Text(text = "Aluno: ${treino.aluno}")

            if (treino.grupoMuscular.isNotBlank()) Text(text = "Grupo muscular: ${treino.grupoMuscular}")
            if (treino.exercicios.isNotBlank()) Text(text = "Exercícios: ${treino.exercicios}")
            if (treino.series.isNotBlank()) Text(text = "Séries: ${treino.series}")
            if (treino.repeticoes.isNotBlank()) Text(text = "Repetições: ${treino.repeticoes}")
            if (treino.observacoes.isNotBlank()) Text(text = "Observações: ${treino.observacoes}")

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onEditarClick) {
                    Text(text = "Editar")
                }

                OutlinedButton(onClick = onExcluirClick) {
                    Text(text = "Apagar")
                }
            }
        }
    }
}
