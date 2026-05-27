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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projetoacademia.model.Aluno

@Composable
fun AlunosScreen(
    onVoltarClick: () -> Unit
) {
    //responsável por quardar a lista dos alunos
    val alunos = remember {
        mutableStateListOf<Aluno>()
    }
    //formulário
    var nome by remember {
        mutableStateOf("")
    }

    var cpf by remember {
        mutableStateOf("")
    }

    var telefone by remember {
        mutableStateOf("")
    }

    var dataNascimento by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var objetivo by remember {
        mutableStateOf("")
    }

    var ativo by remember {
        mutableStateOf(true)
    }

    var mensagemErro by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Cadastro de Alunos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Preencha os dados do aluno e toque em cadastrar.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = nome,
            onValueChange = {
                nome = it
            },
            label = {
                Text(text = "Nome")
            },
            modifier = Modifier.fillMaxWidth(),
            isError = mensagemErro.contains("Nome")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = cpf,
            onValueChange = {
                cpf = it
            },
            label = {
                Text(text = "CPF")
            },
            modifier = Modifier.fillMaxWidth(),
            isError = mensagemErro.contains("CPF")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = telefone,
            onValueChange = {
                telefone = it
            },
            label = {
                Text(text = "Telefone")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = dataNascimento,
            onValueChange = {
                dataNascimento = it
            },
            label = {
                Text(text = "Data de nascimento")
            },
            placeholder = {
                Text(text = "Ex: 10/05/2000")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = objetivo,
            onValueChange = {
                objetivo = it
            },
            label = {
                Text(text = "Objetivo")
            },
            placeholder = {
                Text(text = "Ex: Hipertrofia, emagrecimento, resistência")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = ativo,
                onCheckedChange = {
                    ativo = it
                }
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                //aqui diz os campos que são obrigatórios e impede o cadastro
                if (nome.isBlank()) {
                    mensagemErro = "Nome é obrigatório"
                    return@Button
                }

                if (cpf.isBlank()) {
                    mensagemErro = "CPF é obrigatório"
                    return@Button
                }

                val novoAluno = Aluno(
                    nome = nome,
                    cpf = cpf,
                    telefone = telefone,
                    dataNascimento = dataNascimento,
                    email = email,
                    objetivo = objetivo,
                    ativo = ativo
                )

                alunos.add(novoAluno)

                nome = ""
                cpf = ""
                telefone = ""
                dataNascimento = ""
                email = ""
                objetivo = ""
                ativo = true
                mensagemErro = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cadastrar aluno")
        }

        TextButton(
            onClick = onVoltarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar")
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
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                alunos.forEach { aluno ->
                    AlunoCard(aluno = aluno)
                }
            }
        }
    }
}

@Composable
fun AlunoCard(
    aluno: Aluno
) {
    //destade de cor quando o aluno está inativo
    val corDeFundo = if (aluno.ativo) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        Color(0xFFFFCDD2)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(corDeFundo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(corDeFundo)
                .padding(16.dp)
        ) {
            Text(
                text = aluno.nome,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = "CPF: ${aluno.cpf}")

            if (aluno.telefone.isNotBlank()) {
                Text(text = "Telefone: ${aluno.telefone}")
            }

            if (aluno.dataNascimento.isNotBlank()) {
                Text(text = "Nascimento: ${aluno.dataNascimento}")
            }

            if (aluno.email.isNotBlank()) {
                Text(text = "Email: ${aluno.email}")
            }

            if (aluno.objetivo.isNotBlank()) {
                Text(text = "Objetivo: ${aluno.objetivo}")
            }

            Text(
                text = if (aluno.ativo) "Status: Ativo" else "Status: Inativo",
                color = if (aluno.ativo) Color(0xFF2E7D32) else Color(0xFFC62828)
            )
        }
    }
} 