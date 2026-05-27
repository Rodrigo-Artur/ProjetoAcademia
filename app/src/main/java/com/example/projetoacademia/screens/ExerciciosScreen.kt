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
import com.example.projetoacademia.components.VideoPickerField
import com.example.projetoacademia.data.AppData
import com.example.projetoacademia.model.Exercicio
import com.example.projetoacademia.navigation.AppCreateActions

@Composable
fun ExerciciosScreen(onVoltarClick: () -> Unit) {
    val exercicios = AppData.exercicios

    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf("") }
    var videoUri by remember { mutableStateOf("") }

    var busca by remember { mutableStateOf("") }
    var filtroCategoria by remember { mutableStateOf("Todos") }
    var mensagemErro by remember { mutableStateOf("") }
    var indiceEditando by remember { mutableStateOf<Int?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    val categorias = listOf("Todos", "Peito", "Costas", "Pernas", "Braços", "Ombros", "Abdômen", "Cardio", "Mobilidade")

    val exerciciosFiltrados = exercicios.filter { exercicio ->
        val bateBusca = busca.isBlank() ||
                exercicio.nome.contains(busca, ignoreCase = true) ||
                exercicio.categoria.contains(busca, ignoreCase = true) ||
                exercicio.descricao.contains(busca, ignoreCase = true)
        val bateCategoria = filtroCategoria == "Todos" || exercicio.categoria == filtroCategoria
        bateBusca && bateCategoria
    }

    fun limparFormulario() {
        nome = ""
        categoria = ""
        descricao = ""
        fotoUri = ""
        videoUri = ""
        mensagemErro = ""
        indiceEditando = null
        mostrarFormulario = false
    }

    fun salvarExercicio() {
        if (nome.isBlank()) {
            mensagemErro = "Nome do exercício é obrigatório"
            return
        }
        if (categoria.isBlank()) {
            mensagemErro = "Informe a categoria do exercício"
            return
        }

        val exercicio = Exercicio(nome, categoria, descricao, fotoUri, videoUri)
        val index = indiceEditando
        if (index == null) exercicios.add(exercicio) else exercicios[index] = exercicio
        limparFormulario()
    }

    LaunchedEffect(AppCreateActions.exercicios) {
        if (AppCreateActions.exercicios > 0) {
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
            text = "Biblioteca de exercícios",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Crie exercícios prontos com categoria, descrição, imagem e vídeo. Use o botão + para adicionar.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = busca,
            onValueChange = { busca = it },
            label = { Text(text = "Pesquisar exercício") },
            placeholder = { Text(text = "Busque por nome, categoria ou descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Filtrar por categoria", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            categorias.chunked(3).forEach { linha ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    linha.forEach { item ->
                        FilterChip(
                            selected = filtroCategoria == item,
                            onClick = { filtroCategoria = item },
                            label = { Text(text = item) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ListHeader(
            title = "Exercícios cadastrados",
            count = exerciciosFiltrados.size
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (exerciciosFiltrados.isEmpty()) {
            EmptyState(message = "Nenhum exercício encontrado. Toque no botão + para cadastrar ou ajuste os filtros.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                exerciciosFiltrados.forEach { exercicio ->
                    val index = exercicios.indexOf(exercicio)
                    ExercicioCard(
                        exercicio = exercicio,
                        onEditarClick = {
                            nome = exercicio.nome
                            categoria = exercicio.categoria
                            descricao = exercicio.descricao
                            fotoUri = exercicio.fotoUri
                            videoUri = exercicio.videoUri
                            mensagemErro = ""
                            indiceEditando = index
                            mostrarFormulario = true
                        },
                        onExcluirClick = {
                            exercicios.remove(exercicio)
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
            title = { Text(text = if (indiceEditando == null) "Cadastrar exercício" else "Editar exercício") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text(text = "Nome do exercício") },
                        placeholder = { Text(text = "Ex: Supino reto") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = mensagemErro.contains("Nome")
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = categoria,
                        onValueChange = { categoria = it },
                        label = { Text(text = "Categoria") },
                        placeholder = { Text(text = "Ex: Peito") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = mensagemErro.contains("categoria", ignoreCase = true)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Peito", "Costas", "Pernas").forEach { item ->
                            FilterChip(
                                selected = categoria == item,
                                onClick = { categoria = item },
                                label = { Text(text = item) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = descricao,
                        onValueChange = { descricao = it },
                        label = { Text(text = "Descrição") },
                        placeholder = { Text(text = "Explique execução, postura e cuidados") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ImagePickerField(
                        label = "Selecionar foto do exercício",
                        imageUri = fotoUri,
                        onImageSelected = { fotoUri = it }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    VideoPickerField(
                        label = "Selecionar vídeo do exercício",
                        videoUri = videoUri,
                        onVideoSelected = { videoUri = it }
                    )
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
                Button(onClick = { salvarExercicio() }) {
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
fun ExercicioCard(
    exercicio: Exercicio,
    onEditarClick: () -> Unit,
    onExcluirClick: () -> Unit
) {
    PrettyCard(
        avatarText = exercicio.nome,
        title = exercicio.nome,
        subtitle = exercicio.descricao.ifBlank { "Sem descrição cadastrada" },
        status = { StatusBadge(text = exercicio.categoria.ifBlank { "Sem categoria" }) },
        onEditarClick = onEditarClick,
        onExcluirClick = onExcluirClick
    ) {
        if (exercicio.fotoUri.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            MediaImagePreview(imageUri = exercicio.fotoUri)
        }
        InfoLine(label = "Categoria", value = exercicio.categoria)
        if (exercicio.videoUri.isNotBlank()) {
            InfoLine(label = "Vídeo", value = "Arquivo selecionado")
        }
    }
}
