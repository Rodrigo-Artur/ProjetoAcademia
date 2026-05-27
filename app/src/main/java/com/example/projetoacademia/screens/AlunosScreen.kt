package com.example.projetoacademia.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projetoacademia.data.AppData
import com.example.projetoacademia.model.Aluno

@Composable
fun AlunosScreen(onVoltarClick: () -> Unit) {
    val alunos = AppData.alunos

    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var objetivo by remember { mutableStateOf("") }
    var ativo by remember { mutableStateOf(true) }

    var mensagemErro by remember { mutableStateOf("") }
    var indiceEditando by remember { mutableStateOf<Int?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    fun limparFormulario() {
        nome = ""
        cpf = ""
        telefone = ""
        dataNascimento = ""
        email = ""
        objetivo = ""
        ativo = true
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
            ativo = ativo
        )

        val index = indiceEditando

        if (index == null) {
            alunos.add(aluno)
        } else {
            alunos[index] = aluno
        }

        limparFormulario()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Alunos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Cadastre, edite e remova alunos da academia.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        Button(
            onClick = {
                limparFormulario()
                mostrarFormulario = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cadastrar aluno")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Alunos cadastrados",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (alunos.isEmpty()) {
            Text(text = "Nenhum aluno cadastrado ainda.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                alunos.forEachIndexed { index, aluno ->
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
                            mensagemErro = ""
                            indiceEditando = index
                            mostrarFormulario = true
                        },
                        onExcluirClick = {
                            alunos.removeAt(index)
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

    if (mostrarFormulario) {
        AlertDialog(
            onDismissRequest = { limparFormulario() },
            title = {
                Text(text = if (indiceEditando == null) "Cadastrar aluno" else "Editar aluno")
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
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

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = ativo,
                            onCheckedChange = { ativo = it }
                        )

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
    val corDeFundo = if (aluno.ativo) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        Color(0xFFFFCDD2)
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(corDeFundo)
                .padding(16.dp)
        ) {
            Text(text = aluno.nome, style = MaterialTheme.typography.titleMedium)
            Text(text = "CPF: ${aluno.cpf}")

            if (aluno.telefone.isNotBlank()) Text(text = "Telefone: ${aluno.telefone}")
            if (aluno.dataNascimento.isNotBlank()) Text(text = "Nascimento: ${aluno.dataNascimento}")
            if (aluno.email.isNotBlank()) Text(text = "Email: ${aluno.email}")
            if (aluno.objetivo.isNotBlank()) Text(text = "Objetivo: ${aluno.objetivo}")

            Text(
                text = if (aluno.ativo) "Status: Ativo" else "Status: Inativo",
                color = if (aluno.ativo) Color(0xFF2E7D32) else Color(0xFFC62828)
            )

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
