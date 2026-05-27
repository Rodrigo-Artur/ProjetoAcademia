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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projetoacademia.components.EmptyState
import com.example.projetoacademia.components.InfoLine
import com.example.projetoacademia.components.ListHeader
import com.example.projetoacademia.components.PrettyCard
import com.example.projetoacademia.components.StatusBadge
import com.example.projetoacademia.data.AppData
import com.example.projetoacademia.model.Treino
import com.example.projetoacademia.navigation.AppCreateActions

@Composable
fun TreinosScreen(onVoltarClick: () -> Unit) {
    val alunos = AppData.alunos
    val treinos = AppData.treinos
    val exerciciosProntos = AppData.exercicios

    var alunoSelecionado by remember { mutableStateOf("") }
    var tipoTreino by remember { mutableStateOf("") }
    var grupoMuscular by remember { mutableStateOf("") }
    var exercicios by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var repeticoes by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }
    val exerciciosSelecionados = remember { mutableStateListOf<String>() }

    var busca by remember { mutableStateOf("") }
    var filtroGrupo by remember { mutableStateOf("Todos") }
    var mensagemErro by remember { mutableStateOf("") }
    var indiceEditando by remember { mutableStateOf<Int?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    val grupos = listOf("Todos", "Peito", "Costas", "Pernas", "Braços", "Ombros", "Abdômen", "Cardio")

    val treinosFiltrados = treinos.filter { treino ->
        val bateBusca = busca.isBlank() ||
                treino.aluno.contains(busca, ignoreCase = true) ||
                treino.tipoTreino.contains(busca, ignoreCase = true) ||
                treino.grupoMuscular.contains(busca, ignoreCase = true) ||
                treino.exerciciosSelecionados.any { it.contains(busca, ignoreCase = true) }
        val bateGrupo = filtroGrupo == "Todos" || treino.grupoMuscular == filtroGrupo
        bateBusca && bateGrupo
    }

    fun limparFormulario() {
        alunoSelecionado = ""
        tipoTreino = ""
        grupoMuscular = ""
        exercicios = ""
        series = ""
        repeticoes = ""
        observacoes = ""
        exerciciosSelecionados.clear()
        mensagemErro = ""
        indiceEditando = null
        mostrarFormulario = false
    }

    fun salvarTreino() {
        if (alunos.isEmpty()) {
            mensagemErro = "É necessário cadastrar um aluno antes de cadastrar treinos"
            return
        }
        if (alunoSelecionado.isBlank()) {
            mensagemErro = "Selecione um aluno"
            return
        }
        if (tipoTreino.isBlank()) {
            mensagemErro = "Informe o tipo de treino"
            return
        }

        val treino = Treino(
            aluno = alunoSelecionado,
            tipoTreino = tipoTreino,
            grupoMuscular = grupoMuscular,
            exercicios = exercicios,
            series = series,
            repeticoes = repeticoes,
            observacoes = observacoes,
            exerciciosSelecionados = exerciciosSelecionados.toList()
        )

        val index = indiceEditando
        if (index == null) treinos.add(treino) else treinos[index] = treino
        limparFormulario()
    }

    LaunchedEffect(AppCreateActions.treinos) {
        if (AppCreateActions.treinos > 0) {
            limparFormulario()
            mostrarFormulario = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Gerenciamento de treinos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Monte fichas vinculando alunos a exercícios prontos. Use o botão + para criar novos treinos.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = busca,
            onValueChange = { busca = it },
            label = { Text(text = "Pesquisar treino") },
            placeholder = { Text(text = "Busque por aluno, tipo, grupo ou exercício") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            grupos.chunked(4).forEach { linha ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    linha.forEach { grupo ->
                        FilterChip(
                            selected = filtroGrupo == grupo,
                            onClick = { filtroGrupo = grupo },
                            label = { Text(text = grupo) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ListHeader(title = "Treinos encontrados", count = treinosFiltrados.size)
        Spacer(modifier = Modifier.height(12.dp))

        if (treinosFiltrados.isEmpty()) {
            EmptyState(message = "Nenhum treino encontrado. Toque no botão + para cadastrar ou ajuste os filtros.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                treinosFiltrados.forEach { treino ->
                    val index = treinos.indexOf(treino)
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
                            exerciciosSelecionados.clear()
                            exerciciosSelecionados.addAll(treino.exerciciosSelecionados)
                            mensagemErro = ""
                            indiceEditando = index
                            mostrarFormulario = true
                        },
                        onExcluirClick = {
                            treinos.remove(treino)
                            limparFormulario()
                        }
                    )
                }
            }
        }

        TextButton(onClick = onVoltarClick, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Voltar ao dashboard")
        }
    }

    if (mostrarFormulario) {
        AlertDialog(
            onDismissRequest = { limparFormulario() },
            title = { Text(text = if (indiceEditando == null) "Cadastrar treino" else "Editar treino") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = "Aluno", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (alunos.isEmpty()) {
                        OutlinedCard(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)) {
                            Text(text = "Cadastre pelo menos um aluno antes de criar um treino.", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            alunos.forEach { aluno ->
                                FilterChip(selected = alunoSelecionado == aluno.nome, onClick = { alunoSelecionado = aluno.nome }, label = { Text(text = aluno.nome) })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = tipoTreino, onValueChange = { tipoTreino = it }, label = { Text(text = "Tipo de treino") }, placeholder = { Text(text = "Ex: Hipertrofia") }, modifier = Modifier.fillMaxWidth(), isError = mensagemErro.contains("tipo", ignoreCase = true))
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = grupoMuscular, onValueChange = { grupoMuscular = it }, label = { Text(text = "Grupo muscular") }, placeholder = { Text(text = "Ex: Peito") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Exercícios prontos", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (exerciciosProntos.isEmpty()) {
                        OutlinedCard(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
                            Text(text = "Nenhum exercício pronto cadastrado. Use a aba Exercícios para criar sua biblioteca.", modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            exerciciosProntos.forEach { exercicio ->
                                val selecionado = exerciciosSelecionados.contains(exercicio.nome)
                                FilterChip(
                                    selected = selecionado,
                                    onClick = {
                                        if (selecionado) exerciciosSelecionados.remove(exercicio.nome) else exerciciosSelecionados.add(exercicio.nome)
                                    },
                                    label = { Text(text = "${exercicio.nome} • ${exercicio.categoria}") }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = exercicios, onValueChange = { exercicios = it }, label = { Text(text = "Exercícios extras") }, placeholder = { Text(text = "Use apenas se quiser adicionar algo manualmente") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(value = series, onValueChange = { series = it }, label = { Text(text = "Séries") }, placeholder = { Text(text = "Ex: 4") }, modifier = Modifier.weight(1f))
                        OutlinedTextField(value = repeticoes, onValueChange = { repeticoes = it }, label = { Text(text = "Repetições") }, placeholder = { Text(text = "Ex: 12") }, modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = observacoes, onValueChange = { observacoes = it }, label = { Text(text = "Observações") }, placeholder = { Text(text = "Ex: Aumentar carga a cada semana") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
                    if (mensagemErro.isNotBlank()) {
                        Text(text = mensagemErro, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            },
            confirmButton = {
                Button(onClick = { salvarTreino() }) {
                    Text(text = if (indiceEditando == null) "Salvar" else "Atualizar")
                }
            },
            dismissButton = {
                TextButton(onClick = { limparFormulario() }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}

@Composable
fun TreinoCard(
    treino: Treino,
    onEditarClick: () -> Unit,
    onExcluirClick: () -> Unit
) {
    PrettyCard(
        avatarText = treino.tipoTreino,
        title = treino.tipoTreino,
        subtitle = "Aluno: ${treino.aluno}",
        status = { StatusBadge(text = treino.grupoMuscular.ifBlank { "Treino" }) },
        onEditarClick = onEditarClick,
        onExcluirClick = onExcluirClick
    ) {
        InfoLine(label = "Exercícios prontos", value = treino.exerciciosSelecionados.joinToString(", "))
        InfoLine(label = "Exercícios extras", value = treino.exercicios)
        InfoLine(label = "Séries", value = treino.series)
        InfoLine(label = "Repetições", value = treino.repeticoes)
        InfoLine(label = "Observações", value = treino.observacoes)
    }
}
