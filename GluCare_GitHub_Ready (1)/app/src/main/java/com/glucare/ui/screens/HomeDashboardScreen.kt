package com.glucare.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.theme.GlucoseColors as Palette

@Composable
fun HomeDashboardScreen(
    latestReading: GlucoseReading?,
    onLogGlucose: () -> Unit,
    onOpenReading: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onMedication: (() -> Unit)? = null,
    onMeals: (() -> Unit)? = null,
    onActivity: (() -> Unit)? = null,
    onReminders: (() -> Unit)? = null,
) {
    var medicationDone by rememberSaveable { mutableStateOf(false) }
    var glucoseDone by rememberSaveable { mutableStateOf(false) }

    GlucosePage(modifier) {
        Spacer(Modifier.height(24.dp))
        Text("Good Morning !", fontWeight = FontWeight.Bold, fontSize = 28.sp, color = Palette.Ink)
        SupportingText("Let's keep track of your health today")
        Spacer(Modifier.height(4.dp))
        GlucoseCard(
            Modifier.fillMaxWidth(),
            onClick = latestReading?.let { reading -> { onOpenReading(reading.id) } },
        ) {
            SectionLabel("💧 Latest Glucose Reading")
            Spacer(Modifier.height(10.dp))
            if (latestReading == null) {
                SupportingText("No readings yet")
            } else {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SectionLabel("${latestReading.value} mg/dL")
                    GlucoseBadge(latestReading.status)
                }
                SupportingText(
                    if (latestReading.timestamp >= cutoffDays(1)) "Today, ${readingTime(latestReading.timestamp)}"
                    else readingDateTime(latestReading.timestamp),
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            DashboardTile("💧 Log Glucose", Modifier.weight(1f), Palette.Blue, onLogGlucose)
            DashboardTile("💊 Medication", Modifier.weight(1f), Palette.Sage, onMedication)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            DashboardTile("🍴 Meals", Modifier.weight(1f), Palette.Cream, onMeals)
            DashboardTile("🏃 Activity", Modifier.weight(1f), Palette.Blue, onActivity)
        }
        Spacer(Modifier.height(5.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(1f)) { SectionLabel("Today’s Reminders") }
            TextButton(onClick = { onReminders?.invoke() }, enabled = onReminders != null) {
                Text("View all")
            }
        }
        ReminderRow("💊 Take Metformin", "9:00 AM", medicationDone) { medicationDone = it }
        ReminderRow("💧 Check blood glucose", "12:00 PM", glucoseDone) { glucoseDone = it }
    }
}

@Composable
private fun DashboardTile(
    label: String,
    modifier: Modifier,
    color: androidx.compose.ui.graphics.Color,
    onClick: (() -> Unit)?,
) {
    GlucoseCard(modifier.heightIn(min = 88.dp), color, onClick) {
        Box(Modifier.fillMaxWidth().heightIn(min = 60.dp), contentAlignment = Alignment.Center) {
            Text(label, color = Palette.Ink, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun ReminderRow(label: String, time: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(label, color = Palette.Ink, fontSize = 14.sp)
            SupportingText(time)
        }
        Checkbox(checked = checked, onCheckedChange = onChange)
    }
}
