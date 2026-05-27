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
import com.example.projetoacademia.components.ImagePickerField
import com.example.projetoacademia.components.InfoLine
import com.example.projetoacademia.components.ListHeader
import com.example.projetoacademia.components.MediaImagePreview
import com.example.projetoacademia.components.PrettyCard
import com.example.projetoacademia.components.StatusBadge
import com.example.projetoacademia.data.AppData
import com.example.projetoacademia.model.Aluno
import com.example.projetoacademia.navigation.AppCreateActions

@Composable
fun AlunosScreen(onVoltarClick: () -> Unit) {
    val alunos = AppData.alunos
    val planos = AppData.planos
    val treinadores = AppData.treinadores

    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var objetivo by remember { mutableStateOf("") }
    var ativo by remember { mutableStateOf(true) }
    var fotoUri by remember { mutableStateOf("") }
    var planoSelecionado by remember { mutableStateOf("") }
    var treinadorSelecionado by remember { mutableStateOf("") }

    var busca by remember { mutableStateOf("") }
    var filtroStatus by remember { mutableStateOf("Todos") }
    var mensagemErro by remember { mutableStateOf("") }
    var indiceEditando by remember { mutableStateOf<Int?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    val alunosFiltrados = alunos.filter { aluno ->
        val bateBusca = busca.isBlank() ||
                aluno.nome.contains(busca, ignoreCase = true) ||
                aluno.cpf.contains(busca, ignoreCase = true) ||
                aluno.objetivo.contains(busca, ignoreCase = true) ||
                aluno.plano.contains(busca, ignoreCase = true) ||
                aluno.treinador.contains(busca, ignoreCase = true)

        val bateStatus = when (filtroStatus) {
            "Ativos" -> aluno.ativo
            "Inativos" -> !aluno.ativo
            else -> true
        }

        bateBusca && bateStatus
    }

    fun limparFormulario() {
        nome = ""
        cpf = ""
        telefone = ""
        dataNascimento = ""
        email = ""
        objetivo = ""
        ativo = true
        fotoUri = ""
        planoSelecionado = ""
        treinadorSelecionado = ""
        mensagemErro = ""
        indiceEditando = null
        mostrarFormulario = false
    }

    fun salvarAluno() {
        if (nome.isBlank()) {
            mensagemErro = "Nome é obrigatório"
            return
        }

        if (cpf.isBlank()) {
            mensagemErro = "CPF é obrigatório"
            return
        }

        val aluno = Aluno(
            nome = nome,
            cpf = cpf,
            telefone = telefone,
            dataNascimento = dataNascimento,
            email = email,
            objetivo = objetivo,
            ativo = ativo,
            fotoUri = fotoUri,
            plano = planoSelecionado,
            treinador = treinadorSelecionado
        )

        val index = indiceEditando
        if (index == null) alunos.add(aluno) else alunos[index] = aluno

        limparFormulario()
    }

    LaunchedEffect(AppCreateActions.alunos) {
        if (AppCreateActions.alunos > 0) {
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
            text = "Gerenciamento de alunos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Pesquise alunos, filtre por status, vincule planos e selecione o treinador responsável. Use o botão + para criar.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = busca,
            onValueChange = { busca = it },
            label = { Text(text = "Pesquisar aluno") },
            placeholder = { Text(text = "Busque por nome, CPF, objetivo, plano ou treinador") },
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
            title = "Alunos encontrados",
            count = alunosFiltrados.size
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (alunosFiltrados.isEmpty()) {
            EmptyState(message = "Nenhum aluno encontrado. Toque no botão + para cadastrar ou ajuste sua pesquisa.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                alunosFiltrados.forEach { aluno ->
                    val index = alunos.indexOf(aluno)
                    AlunoCard(
                        aluno = aluno,
                        onEditarClick = {
                            nome = aluno.nome
                            cpf = aluno.cpf
                            telefone = aluno.telefone
                            dataNascimento = aluno.dataNascimento
                            email = aluno.email
                            objetivo = aluno.objetivo
                            ativo = aluno.ativo
                            fotoUri = aluno.fotoUri
                            planoSelecionado = aluno.plano
                            treinadorSelecionado = aluno.treinador
                            mensagemErro = ""
                            indiceEditando = index
                            mostrarFormulario = true
                        },
                        onExcluirClick = {
                            alunos.remove(aluno)
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
            Text(text = "Voltar ao dashboard")
        }
    }

    if (mostrarFormulario) {
        AlertDialog(
            onDismissRequest = { limparFormulario() },
            title = { Text(text = if (indiceEditando == null) "Cadastrar aluno" else "Editar aluno") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    ImagePickerField(
                        label = "Selecionar foto do aluno",
                        imageUri = fotoUri,
                        onImageSelected = { fotoUri = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text(text = "Nome") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = mensagemErro.contains("Nome")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = cpf,
                        onValueChange = { cpf = it },
                        label = { Text(text = "CPF") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = mensagemErro.contains("CPF")
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
                        value = dataNascimento,
                        onValueChange = { dataNascimento = it },
                        label = { Text(text = "Data de nascimento") },
                        placeholder = { Text(text = "Ex: 10/05/2000") },
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

                    OutlinedTextField(
                        value = objetivo,
                        onValueChange = { objetivo = it },
                        label = { Text(text = "Objetivo") },
                        placeholder = { Text(text = "Ex: Hipertrofia") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Plano", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (planos.isEmpty()) {
                        Text(
                            text = "Nenhum plano cadastrado ainda.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            planos.forEach { plano ->
                                FilterChip(
                                    selected = planoSelecionado == plano.nome,
                                    onClick = { planoSelecionado = plano.nome },
                                    label = { Text(text = plano.nome) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Treinador", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (treinadores.isEmpty()) {
                        Text(
                            text = "Nenhum treinador cadastrado ainda.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            treinadores.forEach { treinador ->
                                FilterChip(
                                    selected = treinadorSelecionado == treinador.nome,
                                    onClick = { treinadorSelecionado = treinador.nome },
                                    label = { Text(text = treinador.nome) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = ativo, onCheckedChange = { ativo = it })
                        Text(text = "Aluno ativo")
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
                Button(onClick = { salvarAluno() }) {
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
fun AlunoCard(
    aluno: Aluno,
    onEditarClick: () -> Unit,
    onExcluirClick: () -> Unit
) {
    PrettyCard(
        avatarText = aluno.nome,
        title = aluno.nome,
        subtitle = aluno.objetivo.ifBlank { "Objetivo não informado" },
        status = {
            StatusBadge(
                text = if (aluno.ativo) "Ativo" else "Inativo",
                alert = !aluno.ativo
            )
        },
        onEditarClick = onEditarClick,
        onExcluirClick = onExcluirClick
    ) {
        if (aluno.fotoUri.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            MediaImagePreview(imageUri = aluno.fotoUri)
        }

        InfoLine(label = "Plano", value = aluno.plano)
        InfoLine(label = "Treinador", value = aluno.treinador)
        InfoLine(label = "CPF", value = aluno.cpf)
        InfoLine(label = "Telefone", value = aluno.telefone)
        InfoLine(label = "Nascimento", value = aluno.dataNascimento)
        InfoLine(label = "Email", value = aluno.email)
    }
}
