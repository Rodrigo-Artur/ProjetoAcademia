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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projetoacademia.model.Plano

@Composable
fun PlanosScreen(
    onVoltarClick: () -> Unit
) {
    //aqui guarda a lista de planos
    val planos = remember {
        mutableStateListOf<Plano>()
    }

    var nome by remember {
        mutableStateOf("")
    }

    var valor by remember {
        mutableStateOf("")
    }

    var duracao by remember {
        mutableStateOf("")
    }

    var descricao by remember {
        mutableStateOf("")
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
            text = "Cadastro de Planos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Cadastre os planos disponíveis para os alunos.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = nome,
            onValueChange = {
                nome = it
            },
            label = {
                Text(text = "Nome do plano")
            },
            placeholder = {
                Text(text = "Ex: Mensal")
            },
            modifier = Modifier.fillMaxWidth(),
            isError = mensagemErro.contains("Nome")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = {
                valor = it
            },
            label = {
                Text(text = "Valor")
            },
            placeholder = {
                Text(text = "Ex: 89.90")
            },
            modifier = Modifier.fillMaxWidth(),
            isError = mensagemErro.contains("valor", ignoreCase = true)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = duracao,
            onValueChange = {
                duracao = it
            },
            label = {
                Text(text = "Duração")
            },
            placeholder = {
                Text(text = "Ex: 1 mês")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = descricao,
            onValueChange = {
                descricao = it
            },
            label = {
                Text(text = "Descrição")
            },
            placeholder = {
                Text(text = "Ex: Acesso livre à musculação")
            },
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
                val valorConvertido = valor
                    //aqui converte o , e . para numero
                    .replace(",", ".")
                    //aqui interpreta letras no valor como nulo e cancela o cadsatro
                    .toDoubleOrNull()

                if (nome.isBlank()) {
                    mensagemErro = "Nome do plano é obrigatório"
                    return@Button
                }

                if (valorConvertido == null) {
                    mensagemErro = "Informe um valor válido"
                    return@Button
                }

                if (valorConvertido <= 0.0) {
                    mensagemErro = "O valor do plano deve ser maior que zero"
                    return@Button
                }

                val novoPlano = Plano(
                    nome = nome,
                    valor = valorConvertido,
                    duracao = duracao,
                    descricao = descricao
                )

                planos.add(novoPlano)

                nome = ""
                valor = ""
                duracao = ""
                descricao = ""
                mensagemErro = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cadastrar plano")
        }

        TextButton(
            onClick = onVoltarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Planos cadastrados",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (planos.isEmpty()) {
            Text(text = "Nenhum plano cadastrado ainda.")
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                planos.forEach { plano ->
                    PlanoCard(plano = plano)
                }
            }
        }
    }
}

@Composable
fun PlanoCard(
    plano: Plano
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = plano.nome,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Valor: R$ ${String.format("%.2f", plano.valor)}"
            )

            if (plano.duracao.isNotBlank()) {
                Text(text = "Duração: ${plano.duracao}")
            }

            if (plano.descricao.isNotBlank()) {
                Text(text = "Descrição: ${plano.descricao}")
            }
        }
    }
}