package com.example.projetoacademia.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagamentosScreen(
    onVoltarClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Pagamentos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Aqui ficará o controle de pagamentos.",
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        Button(onClick = onVoltarClick) {
            Text(text = "Voltar")
        }
    }
}