package com.example.projetoacademia.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.projetoacademia.model.Plano

@Composable
fun PlanosScreen(onVoltarClick: () -> Unit) {
    val planos = AppData.planos

    var nome by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var duracao by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    var mensagemErro by remember { mutableStateOf("") }
    var indiceEditando by remember { mutableStateOf<Int?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    fun limparFormulario() {
        nome = ""
        valor = ""
        duracao = ""
        descricao = ""
        mensagemErro = ""
        indiceEditando = null
        mostrarFormulario = false
    }

    fun salvarPlano() {
        val valorConvertido = valor.replace(",", ".").toDoubleOrNull()

        if (nome.isBlank()) {
            mensagemErro = "Nome do plano é obrigatório"
            return
        }

        if (valorConvertido == null) {
            mensagemErro = "Informe um valor válido"
            return
        }

        if (valorConvertido <= 0.0) {
            mensagemErro = "O valor do plano deve ser maior que zero"
            return
        }

        val plano = Plano(nome, valorConvertido, duracao, descricao)
        val index = indiceEditando

        if (index == null) planos.add(plano) else planos[index] = plano

        limparFormulario()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Gerenciamento de planos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Organize os planos da academia com cards mais claros, contagem de registros e ações no menu lateral de cada card.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        Button(
            onClick = {
                limparFormulario()
                mostrarFormulario = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cadastrar plano")
        }

        Spacer(modifier = Modifier.height(24.dp))

        ListHeader(
            title = "Planos cadastrados",
            count = planos.size
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (planos.isEmpty()) {
            EmptyState(message = "Nenhum plano cadastrado ainda. Clique em Cadastrar plano para criar o primeiro.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                planos.forEachIndexed { index, plano ->
                    PlanoCard(
                        plano = plano,
                        onEditarClick = {
                            nome = plano.nome
                            valor = plano.valor.toString()
                            duracao = plano.duracao
                            descricao = plano.descricao
                            mensagemErro = ""
                            indiceEditando = index
                            mostrarFormulario = true
                        },
                        onExcluirClick = {
                            planos.removeAt(index)
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
            Text(text = "Voltar ao início")
        }
    }

    if (mostrarFormulario) {
        AlertDialog(
            onDismissRequest = { limparFormulario() },
            title = { Text(text = if (indiceEditando == null) "Cadastrar plano" else "Editar plano") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text(text = "Nome do plano") },
                        placeholder = { Text(text = "Ex: Mensal") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = mensagemErro.contains("Nome")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = valor,
                        onValueChange = { valor = it },
                        label = { Text(text = "Valor") },
                        placeholder = { Text(text = "Ex: 89.90") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = mensagemErro.contains("valor", ignoreCase = true)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = duracao,
                        onValueChange = { duracao = it },
                        label = { Text(text = "Duração") },
                        placeholder = { Text(text = "Ex: 1 mês") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = descricao,
                        onValueChange = { descricao = it },
                        label = { Text(text = "Descrição") },
                        placeholder = { Text(text = "Ex: Acesso livre à musculação") },
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
                }
            },
            confirmButton = {
                Button(onClick = { salvarPlano() }) {
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
fun PlanoCard(
    plano: Plano,
    onEditarClick: () -> Unit,
    onExcluirClick: () -> Unit
) {
    PrettyCard(
        avatarText = plano.nome,
        title = plano.nome,
        subtitle = plano.descricao.ifBlank { "Sem descrição" },
        status = {
            StatusBadge(text = "R$ ${String.format("%.2f", plano.valor)}")
        },
        onEditarClick = onEditarClick,
        onExcluirClick = onExcluirClick
    ) {
        InfoLine(label = "Duração", value = plano.duracao)
        InfoLine(label = "Valor", value = "R$ ${String.format("%.2f", plano.valor)}")
    }
}
