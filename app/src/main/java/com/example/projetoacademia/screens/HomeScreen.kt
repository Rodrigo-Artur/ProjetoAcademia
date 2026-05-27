package com.example.projetoacademia.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
// funções dos botões
fun HomeScreen(
    // basicamente, quando clicar no botão envia para o arquivo de navegação para mudar de pagina
    onAlunosClick: () -> Unit,
    onPlanosClick: () -> Unit,
    onTreinosClick: () -> Unit,
    onPagamentosClick: () -> Unit,
    onSobreClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Projeto Academia",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Sistema de gerenciamento de academia",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        Button(
            onClick = onAlunosClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Text(text = "Alunos")
        }

        Button(
            onClick = onPlanosClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Text(text = "Planos")
        }

        Button(
            onClick = onTreinosClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Text(text = "Treinos")
        }

        Button(
            onClick = onPagamentosClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Text(text = "Pagamentos")
        }

        Button(
            onClick = onSobreClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sobre")
        }
    }
}