package com.example.projetoacademia.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DonutChartCard(
    title: String,
    subtitle: String,
    segments: List<DonutSegment>,
    onClick: () -> Unit
) {
    val total = segments.sumOf { it.value }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
        onClick = onClick
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

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                DonutChart(
                    segments = segments,
                    modifier = Modifier.size(170.dp)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Total: $total",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    segments.forEach { segment ->
                        DonutLegendItem(segment = segment)
                    }
                }
            }
        }
    }
}

@Composable
fun DonutChart(
    segments: List<DonutSegment>,
    modifier: Modifier = Modifier
) {
    val total = segments.sumOf { it.value }.coerceAtLeast(1)

    Canvas(modifier = modifier) {
        val strokeWidth = size.minDimension * 0.18f
        val chartSize = size.minDimension - strokeWidth
        val topLeft = Offset(strokeWidth / 2f, strokeWidth / 2f)
        val arcSize = Size(chartSize, chartSize)

        var startAngle = -90f

        segments.forEach { segment ->
            if (segment.value > 0) {
                val sweepAngle = 360f * segment.value / total

                drawArc(
                    color = segment.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Butt
                    )
                )

                startAngle += sweepAngle
            }
        }
    }
}

@Composable
fun DonutLegendItem(segment: DonutSegment) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            modifier = Modifier.size(14.dp),
            color = segment.color,
            shape = RoundedCornerShape(50)
        ) {}

        Text(
            text = "${segment.label}: ${segment.value}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

data class DonutSegment(
    val label: String,
    val value: Int,
    val color: Color
)
