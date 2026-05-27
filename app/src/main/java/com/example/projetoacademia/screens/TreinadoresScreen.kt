package com.example.projetoacademia.screens

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projetoacademia.components.EmptyState
import com.example.projetoacademia.components.InfoLine
import com.example.projetoacademia.components.ListHeader
import com.example.projetoacademia.components.PrettyCard
import com.example.projetoacademia.components.StatusBadge
import com.example.projetoacademia.data.AppData
import com.example.projetoacademia.model.Treinador
import com.example.projetoacademia.navigation.AppCreateActions

@Composable
fun TreinadoresScreen(onVoltarClick: () -> Unit) {
    val treinadores = AppData.treinadores
    val alunos = AppData.alunos

    var nome by remember { mutableStateOf("") }
    var especialidade by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var ativo by remember { mutableStateOf(true) }

    var busca by remember { mutableStateOf("") }
    var filtroStatus by remember { mutableStateOf("Todos") }
    var mensagemErro by remember { mutableStateOf("") }
    var indiceEditando by remember { mutableStateOf<Int?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    val treinadoresFiltrados = treinadores.filter { treinador ->
        val bateBusca = busca.isBlank() ||
                treinador.nome.contains(busca, ignoreCase = true) ||
                treinador.especialidade.contains(busca, ignoreCase = true)

        val bateStatus = when (filtroStatus) {
            "Ativos" -> treinador.ativo
            "Inativos" -> !treinador.ativo
            else -> true
        }

        bateBusca && bateStatus
    }

    fun limparFormulario() {
        nome = ""
        especialidade = ""
        telefone = ""
        email = ""
        ativo = true
        mensagemErro = ""
        indiceEditando = null
        mostrarFormulario = false
    }

    fun salvarTreinador() {
        if (nome.isBlank()) {
            mensagemErro = "Nome do treinador é obrigatório"
            return
        }

        if (especialidade.isBlank()) {
            mensagemErro = "Especialidade é obrigatória"
            return
        }

        val treinador = Treinador(nome, especialidade, telefone, email, ativo)
        val index = indiceEditando
        if (index == null) treinadores.add(treinador) else treinadores[index] = treinador
        limparFormulario()
    }

    LaunchedEffect(AppCreateActions.treinadores) {
        if (AppCreateActions.treinadores > 0) {
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
            text = "Gerenciamento de treinadores",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Monitore especialidades e veja quantos alunos cada profissional acompanha. Use o botão + para criar.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = busca,
            onValueChange = { busca = it },
            label = { Text(text = "Pesquisar treinador") },
            placeholder = { Text(text = "Busque por nome ou especialidade") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Todos", "Ativos", "Inativos").forEach { status ->
                FilterChip(
                    selected = filtroStatus == status,
                    onClick = { filtroStatus = status },
                    label = { Text(text = status) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ListHeader(
            title = "Treinadores encontrados",
            count = treinadoresFiltrados.size
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (treinadoresFiltrados.isEmpty()) {
            EmptyState(message = "Nenhum treinador encontrado. Toque no botão + para cadastrar ou ajuste a pesquisa.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                treinadoresFiltrados.forEach { treinador ->
                    val index = treinadores.indexOf(treinador)
                    TreinadorCard(
                        treinador = treinador,
                        quantidadeAlunos = alunos.count { it.treinador == treinador.nome },
                        onEditarClick = {
                            nome = treinador.nome
                            especialidade = treinador.especialidade
                            telefone = treinador.telefone
                            email = treinador.email
                            ativo = treinador.ativo
                            mensagemErro = ""
                            indiceEditando = index
                            mostrarFormulario = true
                        },
                        onExcluirClick = {
                            treinadores.remove(treinador)
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
            title = { Text(text = if (indiceEditando == null) "Cadastrar treinador" else "Editar treinador") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text(text = "Nome") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = mensagemErro.contains("Nome")
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = especialidade,
                        onValueChange = { especialidade = it },
                        label = { Text(text = "Especialidade") },
                        placeholder = { Text(text = "Ex: Hipertrofia") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = mensagemErro.contains("Especialidade")
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = telefone,
                        onValueChange = { telefone = it },
                        label = { Text(text = "Telefone") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = "Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = ativo, onCheckedChange = { ativo = it })
                        Text(text = "Treinador ativo")
                    }
                    if (mensagemErro.isNotBlank()) {
                        Text(
                            text = mensagemErro,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { salvarTreinador() }) {
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
fun TreinadorCard(
    treinador: Treinador,
    quantidadeAlunos: Int,
    onEditarClick: () -> Unit,
    onExcluirClick: () -> Unit
) {
    PrettyCard(
        avatarText = treinador.nome,
        title = treinador.nome,
        subtitle = treinador.especialidade,
        status = {
            StatusBadge(
                text = if (treinador.ativo) "Ativo" else "Inativo",
                alert = !treinador.ativo
            )
        },
        onEditarClick = onEditarClick,
        onExcluirClick = onExcluirClick
    ) {
        InfoLine(label = "Alunos vinculados", value = quantidadeAlunos.toString())
        InfoLine(label = "Telefone", value = treinador.telefone)
        InfoLine(label = "Email", value = treinador.email)
    }
}
