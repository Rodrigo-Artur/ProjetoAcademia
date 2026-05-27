package com.example.projetoacademia.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projetoacademia.components.DonutChartCard
import com.example.projetoacademia.components.DonutSegment
import com.example.projetoacademia.data.AppData

@Composable
fun HomeScreen(
    onNavigateToAlunos: () -> Unit,
    onNavigateToPlanos: () -> Unit,
    onNavigateToPagamentos: () -> Unit,
    onNavigateToTreinadores: () -> Unit
) {
    val totalAlunos = AppData.alunos.size
    val alunosAtivos = AppData.alunos.count { it.ativo }
    val alunosInativos = totalAlunos - alunosAtivos

    val valorRecebido = AppData.pagamentos
        .filter { it.situacao == "Pago" }
        .sumOf { it.valor }

    val valorPendente = AppData.pagamentos
        .filter { it.situacao == "Pendente" }
        .sumOf { it.valor }

    val valorAtrasado = AppData.pagamentos
        .filter { it.situacao == "Atrasado" }
        .sumOf { it.valor }

    val remuneracaoTotal = valorRecebido + valorPendente + valorAtrasado

    val alunosPorPlano = AppData.planos.map { plano ->
        DonutSegment(
            label = plano.nome,
            value = AppData.alunos.count { it.plano == plano.nome },
            color = corDoPlano(plano.nome)
        )
    }

    val pagamentosPorStatus = listOf(
        DonutSegment("Pagos", AppData.pagamentos.count { it.situacao == "Pago" }, Color(0xFF2E7D32)),
        DonutSegment("Pendentes", AppData.pagamentos.count { it.situacao == "Pendente" }, Color(0xFFF9A825)),
        DonutSegment("Atrasados", AppData.pagamentos.count { it.situacao == "Atrasado" }, Color(0xFFC62828))
    )

    val valoresFinanceiros = listOf(
        DonutSegment("Recebido: ${formatarDinheiro(valorRecebido)}", valorRecebido.toInt(), Color(0xFF2E7D32)),
        DonutSegment("Pendente: ${formatarDinheiro(valorPendente)}", valorPendente.toInt(), Color(0xFFF9A825)),
        DonutSegment("Atrasado: ${formatarDinheiro(valorAtrasado)}", valorAtrasado.toInt(), Color(0xFFC62828))
    )

    val alunosPorTreinador = AppData.treinadores.map { treinador ->
        DonutSegment(
            label = treinador.nome,
            value = AppData.alunos.count { it.treinador == treinador.nome },
            color = corDoTreinador(treinador.nome)
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Acompanhe a academia com gráficos redondos. Toque em cada card para abrir a área relacionada.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard("Alunos", totalAlunos.toString(), Modifier.weight(1f))
                MetricCard("Ativos", alunosAtivos.toString(), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard("Total financeiro", formatarDinheiro(remuneracaoTotal), Modifier.weight(1f))
                MetricCard("Recebido", formatarDinheiro(valorRecebido), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard("Pendente", formatarDinheiro(valorPendente), Modifier.weight(1f))
                MetricCard("Atrasado", formatarDinheiro(valorAtrasado), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            DonutChartCard(
                title = "Alunos por status",
                subtitle = "Total de alunos ativos e inativos",
                segments = listOf(
                    DonutSegment("Ativos", alunosAtivos, Color(0xFF2E7D32)),
                    DonutSegment("Inativos", alunosInativos, Color(0xFFC62828))
                ),
                onClick = onNavigateToAlunos
            )

            Spacer(modifier = Modifier.height(14.dp))

            DonutChartCard(
                title = "Alunos por plano",
                subtitle = "Quantidade de alunos vinculados a cada plano",
                segments = alunosPorPlano,
                onClick = onNavigateToPlanos
            )

            Spacer(modifier = Modifier.height(14.dp))

            DonutChartCard(
                title = "Pagamentos por quantidade",
                subtitle = "Pagos, pendentes e atrasados separados por cor",
                segments = pagamentosPorStatus,
                onClick = onNavigateToPagamentos
            )

            Spacer(modifier = Modifier.height(14.dp))

            DonutChartCard(
                title = "Resumo financeiro",
                subtitle = "Total: ${formatarDinheiro(remuneracaoTotal)}",
                segments = valoresFinanceiros,
                onClick = onNavigateToPagamentos
            )

            Spacer(modifier = Modifier.height(14.dp))

            DonutChartCard(
                title = "Alunos por treinador",
                subtitle = "Monitore quantos alunos cada treinador acompanha",
                segments = alunosPorTreinador,
                onClick = onNavigateToTreinadores
            )
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

fun formatarDinheiro(valor: Double): String {
    return "R$ ${String.format("%.2f", valor)}"
}

fun corDoPlano(nome: String): Color {
    return when (nome) {
        "Mensal" -> Color(0xFF1565C0)
        "Trimestral" -> Color(0xFF6A1B9A)
        "Premium" -> Color(0xFFEF6C00)
        else -> Color(0xFF00897B)
    }
}

fun corDoTreinador(nome: String): Color {
    return when (nome) {
        "Marina Rocha" -> Color(0xFF0288D1)
        "Rafael Nunes" -> Color(0xFF7CB342)
        "Patrícia Gomes" -> Color(0xFFAD1457)
        else -> Color(0xFF6D4C41)
    }
}
