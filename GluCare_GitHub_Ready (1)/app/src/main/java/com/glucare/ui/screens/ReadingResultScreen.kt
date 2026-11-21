package com.glucare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.theme.GlucoseColors as Palette

@Composable
fun ReadingResultScreen(
    reading: GlucoseReading,
    onBack: () -> Unit,
    onDone: () -> Unit,
    onViewHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GlucosePage(modifier, "Reading Result", onBack) {
        Box(Modifier.align(Alignment.CenterHorizontally), contentAlignment = Alignment.BottomCenter) {
            Column(
                Modifier.size(164.dp).background(Palette.Sage, CircleShape),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("💧", fontSize = 23.sp)
                Text(reading.value.toString(), fontSize = 35.sp, fontWeight = FontWeight.Bold, color = Palette.Ink)
                SupportingText("mg/dL")
                Spacer(Modifier.height(20.dp))
            }
            GlucoseBadge(reading.status)
        }
        Spacer(Modifier.height(4.dp))
        GlucoseCard(Modifier.fillMaxWidth()) {
            SectionLabel(if (reading.status == GlucoseStatus.Normal) "Great" else "Reading logged")
            SupportingText("This reading is in the ${reading.status.label.lowercase()} range shown below.")
        }
        PrototypeRange(reading.status)
        Spacer(Modifier.height(4.dp))
        GlucoseCard(Modifier.fillMaxWidth()) {
            SectionLabel("General Tips")
            SupportingText("💡 Keep tracking your glucose regularly and follow your healthcare plan.")
        }
        Spacer(Modifier.height(10.dp))
        PrimaryGlucoseButton("Done", onDone, Modifier.fillMaxWidth(0.74f).align(Alignment.CenterHorizontally))
        OutlinedButton(
            onClick = onViewHistory,
            modifier = Modifier.fillMaxWidth(0.74f).align(Alignment.CenterHorizontally).heightIn(min = 48.dp),
            shape = RoundedCornerShape(13.dp), border = BorderStroke(1.dp, Palette.Accent),
        ) { Text("View History", color = Palette.Accent, fontWeight = FontWeight.SemiBold) }
    }
}

@Composable
private fun PrototypeRange(selected: GlucoseStatus) {
    val labels = listOf(GlucoseStatus.Low to "< 70", GlucoseStatus.Normal to "70 – 140", GlucoseStatus.High to "> 140")
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth()) {
            labels.forEach { (status, _) ->
                Text(
                    if (selected == status) "▼" else " ",
                    Modifier.weight(1f), textAlign = TextAlign.Center, color = Palette.Ink, fontSize = 11.sp,
                )
            }
        }
        Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))) {
            Box(Modifier.weight(1f).height(13.dp).background(Palette.Pink))
            Box(Modifier.weight(1f).height(13.dp).background(Palette.LightGreen))
            Box(Modifier.weight(1f).height(13.dp).background(Palette.Blue))
        }
        Row(Modifier.fillMaxWidth().padding(top = 5.dp)) {
            labels.forEach { (status, range) ->
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    SupportingText(status.label)
                    SupportingText(range)
                }
            }
        }
        SupportingText("Prototype ranges", Modifier.padding(top = 4.dp))
    }
}
