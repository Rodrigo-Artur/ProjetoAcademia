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
import com.example.projetoacademia.model.Pagamento
import com.example.projetoacademia.navigation.AppCreateActions

@Composable
fun PagamentosScreen(onVoltarClick: () -> Unit) {
    val alunos = AppData.alunos
    val planos = AppData.planos
    val pagamentos = AppData.pagamentos

    var alunoSelecionado by remember { mutableStateOf("") }
    var planoSelecionado by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var formaPagamento by remember { mutableStateOf("") }
    var situacaoSelecionada by remember { mutableStateOf("") }

    var mensagemErro by remember { mutableStateOf("") }
    var indiceEditando by remember { mutableStateOf<Int?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    val situacoes = listOf("Pago", "Pendente", "Atrasado")

    fun limparFormulario() {
        alunoSelecionado = ""
        planoSelecionado = ""
        data = ""
        valor = ""
        formaPagamento = ""
        situacaoSelecionada = ""
        mensagemErro = ""
        indiceEditando = null
        mostrarFormulario = false
    }

    fun salvarPagamento() {
        val valorConvertido = valor.replace(",", ".").toDoubleOrNull()

        if (alunos.isEmpty()) {
            mensagemErro = "É necessário cadastrar um aluno antes de registrar pagamentos"
            return
        }
        if (planos.isEmpty()) {
            mensagemErro = "É necessário cadastrar um plano antes de registrar pagamentos"
            return
        }
        if (alunoSelecionado.isBlank()) {
            mensagemErro = "Selecione um aluno"
            return
        }
        if (planoSelecionado.isBlank()) {
            mensagemErro = "Selecione um plano"
            return
        }
        if (valorConvertido == null) {
            mensagemErro = "Informe um valor válido"
            return
        }
        if (valorConvertido <= 0.0) {
            mensagemErro = "O valor do pagamento deve ser maior que zero"
            return
        }
        if (situacaoSelecionada.isBlank()) {
            mensagemErro = "Selecione a situação do pagamento"
            return
        }

        val pagamento = Pagamento(alunoSelecionado, planoSelecionado, data, valorConvertido, formaPagamento, situacaoSelecionada)
        val index = indiceEditando
        if (index == null) pagamentos.add(pagamento) else pagamentos[index] = pagamento
        limparFormulario()
    }

    LaunchedEffect(AppCreateActions.pagamentos) {
        if (AppCreateActions.pagamentos > 0) {
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
            text = "Gerenciamento de pagamentos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Controle mensalidades com cards financeiros. Use o botão + para registrar pagamentos.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        ListHeader(
            title = "Pagamentos registrados",
            count = pagamentos.size
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (pagamentos.isEmpty()) {
            EmptyState(message = "Nenhum pagamento registrado ainda. Toque no botão + para registrar.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                pagamentos.forEachIndexed { index, pagamento ->
                    PagamentoCard(
                        pagamento = pagamento,
                        onEditarClick = {
                            alunoSelecionado = pagamento.aluno
                            planoSelecionado = pagamento.plano
                            data = pagamento.data
                            valor = pagamento.valor.toString()
                            formaPagamento = pagamento.formaPagamento
                            situacaoSelecionada = pagamento.situacao
                            mensagemErro = ""
                            indiceEditando = index
                            mostrarFormulario = true
                        },
                        onExcluirClick = {
                            pagamentos.removeAt(index)
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
            title = { Text(text = if (indiceEditando == null) "Registrar pagamento" else "Editar pagamento") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = "Aluno", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (alunos.isEmpty()) {
                        OutlinedCard(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)) {
                            Text(text = "Cadastre pelo menos um aluno antes de registrar pagamentos.", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            alunos.forEach { aluno ->
                                FilterChip(selected = alunoSelecionado == aluno.nome, onClick = { alunoSelecionado = aluno.nome }, label = { Text(text = aluno.nome) })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Plano", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (planos.isEmpty()) {
                        OutlinedCard(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)) {
                            Text(text = "Cadastre pelo menos um plano antes de registrar pagamentos.", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            planos.forEach { plano ->
                                FilterChip(
                                    selected = planoSelecionado == plano.nome,
                                    onClick = {
                                        planoSelecionado = plano.nome
                                        valor = plano.valor.toString()
                                    },
                                    label = { Text(text = "${plano.nome} - R$ ${String.format("%.2f", plano.valor)}") }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = data, onValueChange = { data = it }, label = { Text(text = "Data") }, placeholder = { Text(text = "Ex: 20/05/2026") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = valor, onValueChange = { valor = it }, label = { Text(text = "Valor") }, placeholder = { Text(text = "Ex: 89.90") }, modifier = Modifier.fillMaxWidth(), isError = mensagemErro.contains("valor", ignoreCase = true))
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = formaPagamento, onValueChange = { formaPagamento = it }, label = { Text(text = "Forma de pagamento") }, placeholder = { Text(text = "Ex: Pix, dinheiro, cartão") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Situação", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        situacoes.forEach { situacao ->
                            FilterChip(selected = situacaoSelecionada == situacao, onClick = { situacaoSelecionada = situacao }, label = { Text(text = situacao) })
                        }
                    }
                    if (mensagemErro.isNotBlank()) {
                        Text(text = mensagemErro, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            },
            confirmButton = {
                Button(onClick = { salvarPagamento() }) {
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
fun PagamentoCard(
    pagamento: Pagamento,
    onEditarClick: () -> Unit,
    onExcluirClick: () -> Unit
) {
    PrettyCard(
        avatarText = pagamento.aluno,
        title = pagamento.aluno,
        subtitle = "Plano: ${pagamento.plano}",
        status = { StatusBadge(text = pagamento.situacao, alert = pagamento.situacao == "Atrasado") },
        onEditarClick = onEditarClick,
        onExcluirClick = onExcluirClick
    ) {
        InfoLine(label = "Data", value = pagamento.data)
        InfoLine(label = "Valor", value = "R$ ${String.format("%.2f", pagamento.valor)}")
        InfoLine(label = "Forma de pagamento", value = pagamento.formaPagamento)
    }
}
