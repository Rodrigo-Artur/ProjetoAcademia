package com.example.projetoacademia.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projetoacademia.data.AppData

@Composable
fun HomeScreen(
    onNavigateToAlunos: () -> Unit,
    onNavigateToPlanos: () -> Unit,
    onNavigateToPagamentos: () -> Unit
) {
    val totalAlunos = AppData.alunos.size
    val alunosAtivos = AppData.alunos.count { it.ativo }
    val alunosInativos = totalAlunos - alunosAtivos

    val alunosPorPlano = AppData.planos.map { plano ->
        ChartSegment(
            label = plano.nome,
            value = AppData.alunos.count { it.plano == plano.nome },
            color = corDoPlano(plano.nome)
        )
    }

    val pagamentosPorStatus = listOf(
        ChartSegment("Pagos", AppData.pagamentos.count { it.situacao == "Pago" }, Color(0xFF2E7D32)),
        ChartSegment("Pendentes", AppData.pagamentos.count { it.situacao == "Pendente" }, Color(0xFFF9A825)),
        ChartSegment("Atrasados", AppData.pagamentos.count { it.situacao == "Atrasado" }, Color(0xFFC62828))
    )

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
                text = "Acompanhe alunos, planos e pagamentos em gráficos rápidos. Toque em um gráfico para abrir a página relacionada.",
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
                MetricCard("Planos ativos", AppData.planos.size.toString(), Modifier.weight(1f))
                MetricCard("Pagamentos", AppData.pagamentos.size.toString(), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            DashboardChartCard(
                title = "Alunos por status",
                subtitle = "Total de alunos e quantos estão ativos no sistema",
                segments = listOf(
                    ChartSegment("Ativos", alunosAtivos, Color(0xFF2E7D32)),
                    ChartSegment("Inativos", alunosInativos, Color(0xFFC62828))
                ),
                onClick = onNavigateToAlunos
            )

            Spacer(modifier = Modifier.height(14.dp))

            DashboardChartCard(
                title = "Alunos por plano",
                subtitle = "Distribuição dos alunos entre os planos cadastrados",
                segments = alunosPorPlano,
                onClick = onNavigateToPlanos
            )

            Spacer(modifier = Modifier.height(14.dp))

            DashboardChartCard(
                title = "Pagamentos por situação",
                subtitle = "Pagos, pendentes e atrasados separados por cor",
                segments = pagamentosPorStatus,
                onClick = onNavigateToPagamentos
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
                style = MaterialTheme.typography.headlineMedium,
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

@Composable
fun DashboardChartCard(
    title: String,
    subtitle: String,
    segments: List<ChartSegment>,
    onClick: () -> Unit
) {
    val total = segments.sumOf { it.value }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (total == 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {}
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .clip(RoundedCornerShape(50))
                ) {
                    segments.filter { it.value > 0 }.forEach { segment ->
                        Spacer(
                            modifier = Modifier
                                .weight(segment.value.toFloat())
                                .height(28.dp)
                                .background(segment.color)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                segments.forEach { segment ->
                    ChartLegendItem(segment = segment)
                }
            }
        }
    }
}

@Composable
fun ChartLegendItem(segment: ChartSegment) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Spacer(
            modifier = Modifier
                .height(18.dp)
                .padding(top = 2.dp)
                .clip(RoundedCornerShape(50))
                .background(segment.color)
                .weight(0.08f)
        )

        Text(
            text = "${segment.label}: ${segment.value}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

data class ChartSegment(
    val label: String,
    val value: Int,
    val color: Color
)

fun corDoPlano(nome: String): Color {
    return when (nome) {
        "Mensal" -> Color(0xFF1565C0)
        "Trimestral" -> Color(0xFF6A1B9A)
        "Premium" -> Color(0xFFEF6C00)
        else -> Color(0xFF00897B)
    }
}
