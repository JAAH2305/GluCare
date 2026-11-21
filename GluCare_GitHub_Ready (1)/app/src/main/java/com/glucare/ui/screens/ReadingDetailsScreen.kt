package com.glucare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.theme.GlucoseColors as Palette

@Composable
fun ReadingDetailsScreen(
    reading: GlucoseReading,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var confirmDelete by rememberSaveable(reading.id) { mutableStateOf(false) }
    GlucosePage(modifier, "Reading Details", onBack) {
        GlucoseCard(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Text(reading.value.toString(), fontSize = 34.sp, fontWeight = FontWeight.Bold, color = Palette.Ink)
                    SupportingText(" mg/dL")
                }
                GlucoseBadge(reading.status)
            }
            SupportingText(readingDateTime(reading.timestamp), Modifier.align(Alignment.CenterHorizontally).padding(top = 7.dp))
        }
        SectionLabel("Reading Information")
        GlucoseCard(Modifier.fillMaxWidth(), color = Color.White, outlined = true) {
            InformationRow("Date & Time", readingDateTime(reading.timestamp))
            InformationRow("Tag", reading.tag ?: "—")
            InformationRow("Note", reading.notes.ifBlank { "—" })
            InformationRow("Added from", "Manual entry")
        }
        Spacer(Modifier.height(100.dp))
        PrimaryGlucoseButton("Edit Reading", onEdit, Modifier.fillMaxWidth(0.75f).align(Alignment.CenterHorizontally))
        OutlinedButton(
            onClick = { confirmDelete = true },
            modifier = Modifier.fillMaxWidth(0.75f).align(Alignment.CenterHorizontally).heightIn(min = 48.dp),
            shape = RoundedCornerShape(13.dp), border = BorderStroke(1.dp, Palette.Red),
        ) { Text("Delete Reading", color = Palette.Red, fontWeight = FontWeight.SemiBold) }
    }
    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("Delete reading?") },
            text = { Text("This removes the reading from your history.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmDelete = false
                    onDelete()
                }) { Text("Delete", color = Palette.Red) }
            },
            dismissButton = { TextButton(onClick = { confirmDelete = false }) { Text("Cancel") } },
        )
    }
}

@Composable
private fun InformationRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(label, Modifier.weight(0.85f), color = Palette.Ink, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        SupportingText(value, Modifier.weight(1.15f))
    }
}
