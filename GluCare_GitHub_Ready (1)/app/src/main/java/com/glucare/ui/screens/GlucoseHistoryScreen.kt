package com.glucare.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.theme.GlucoseColors as Palette

private enum class HistoryPeriod(val label: String, val days: Int) {
    Week("Week", 7), Month("Month", 30), Quarter("3 Month", 90),
}

@Composable
fun GlucoseHistoryScreen(
    readings: List<GlucoseReading>,
    onBack: () -> Unit,
    onOpenReading: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val period = HistoryPeriod.entries[selectedIndex]
    val cutoff = cutoffDays(period.days)
    val filtered = readings.filter { it.timestamp >= cutoff }
        .sortedWith(compareByDescending<GlucoseReading> { it.timestamp }.thenByDescending { it.id })

    GlucosePage(modifier, "Glucose History", onBack) {
        Surface(color = Palette.Muted, shape = RoundedCornerShape(14.dp)) {
            Row(Modifier.fillMaxWidth()) {
                HistoryPeriod.entries.forEachIndexed { index, option ->
                    val selected = index == selectedIndex
                    Surface(
                        selected = selected,
                        onClick = { selectedIndex = index },
                        modifier = Modifier.weight(1f),
                        color = if (selected) Palette.Accent else Palette.Muted,
                        contentColor = if (selected) Color.White else Palette.Ink,
                        shape = RoundedCornerShape(14.dp),
                    ) {
                        Box(Modifier.heightIn(min = 48.dp).padding(4.dp), contentAlignment = Alignment.Center) {
                            Text(option.label, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(18.dp))
        GlucoseCard(Modifier.fillMaxWidth(), color = Color.White, outlined = true) {
            SectionLabel("Glucose Trend")
            if (filtered.isEmpty()) {
                SupportingText("No readings for this period", Modifier.padding(top = 10.dp))
            } else {
                GlucoseTrend(filtered.asReversed())
            }
        }
        Spacer(Modifier.height(18.dp))
        SectionLabel("Recent Readings")
        if (filtered.isEmpty()) {
            SupportingText("Saved readings will appear here.")
        }
        filtered.forEach { reading ->
            GlucoseCard(
                Modifier.fillMaxWidth(), color = Color.White, outlined = true,
                onClick = { onOpenReading(reading.id) },
            ) {
                Row(
                    Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(Modifier.size(18.dp).background(reading.status.background, CircleShape))
                    Column(Modifier.weight(1f)) {
                        SectionLabel("${reading.value} mg/dL")
                        SupportingText(readingDateTime(reading.timestamp))
                    }
                    GlucoseBadge(reading.status)
                }
            }
        }
    }
}

@Composable
private fun GlucoseTrend(readings: List<GlucoseReading>) {
    val first = readings.first()
    val last = readings.last()
    val ceiling = maxOf(200L, ((readings.maxOf { it.value }.toLong() + 49) / 50) * 50)
    val duration = last.timestamp - first.timestamp
    val description = "Glucose trend, ${readings.size} readings. First ${first.value}, latest ${last.value} mg/dL."
    Row(Modifier.fillMaxWidth().padding(top = 10.dp)) {
        Column(
            Modifier.height(115.dp).padding(end = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(ceiling.toString(), color = Palette.SecondaryText, fontSize = 10.sp)
            Text((ceiling / 2).toString(), color = Palette.SecondaryText, fontSize = 10.sp)
            Text("0", color = Palette.SecondaryText, fontSize = 10.sp)
        }
        Canvas(
            Modifier.weight(1f).height(115.dp).padding(horizontal = 3.dp, vertical = 5.dp)
                .semantics { contentDescription = description },
        ) {
            for (division in 0..4) {
                val y = size.height * division / 4
                drawLine(Palette.Border, Offset(0f, y), Offset(size.width, y), 1.dp.toPx())
            }
            val points = readings.mapIndexed { index, reading ->
                val x = when {
                    readings.size == 1 -> size.width / 2
                    duration == 0L -> size.width * index / readings.lastIndex
                    else -> size.width * (reading.timestamp - first.timestamp).toFloat() / duration.toFloat()
                }
                Offset(x, size.height * (1f - reading.value.toFloat() / ceiling.toFloat()))
            }
            val line = Path().apply {
                moveTo(points.first().x, points.first().y)
                points.drop(1).forEach { lineTo(it.x, it.y) }
            }
            if (points.size > 1) {
                val area = Path().apply {
                    addPath(line)
                    lineTo(points.last().x, size.height)
                    lineTo(points.first().x, size.height)
                    close()
                }
                drawPath(area, Brush.verticalGradient(listOf(Palette.LightGreen, Color.Transparent)))
                drawPath(line, Palette.Accent, style = Stroke(width = 2.dp.toPx()))
            }
            points.forEach { drawCircle(Palette.Accent, 3.dp.toPx(), it) }
        }
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(readingDate(first.timestamp), color = Palette.SecondaryText, fontSize = 10.sp)
        if (readingDate(first.timestamp) != readingDate(last.timestamp)) {
            Text(readingDate(last.timestamp), color = Palette.SecondaryText, fontSize = 10.sp)
        }
    }
}
