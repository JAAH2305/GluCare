package com.glucare.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.theme.GlucoseColors as Palette
import java.util.Calendar

@Composable
fun AddGlucoseReadingScreen(
    onBack: () -> Unit,
    onSave: (value: Int, timestamp: Long, tag: String?, notes: String) -> Unit,
    modifier: Modifier = Modifier,
    existing: GlucoseReading? = null,
) {
    var value by rememberSaveable(existing?.id) { mutableStateOf(existing?.value?.toString().orEmpty()) }
    var timestamp by rememberSaveable(existing?.id) { mutableLongStateOf(existing?.timestamp ?: System.currentTimeMillis()) }
    var tag by rememberSaveable(existing?.id) { mutableStateOf(existing?.tag) }
    var notes by rememberSaveable(existing?.id) { mutableStateOf(existing?.notes.orEmpty()) }
    var attemptedSave by rememberSaveable(existing?.id) { mutableStateOf(false) }
    val context = LocalContext.current
    val dateIsInFuture = timestamp > System.currentTimeMillis()
    val parsedValue = value.toIntOrNull()?.takeIf { it > 0 }
    val colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Palette.Field,
        unfocusedContainerColor = Palette.Field,
        focusedBorderColor = Palette.Accent,
        unfocusedBorderColor = Palette.Border,
    )
    val tagOptions = listOf("🍏 Fasting" to "Fasting", "🍴 Before meal" to "Before meal",
        "🍴 After meal" to "After meal", "🏃 Before exercise" to "Before exercise")

    GlucosePage(modifier, if (existing == null) "Log Glucose Reading" else "Edit Reading", onBack) {
        Box(
            Modifier.align(Alignment.CenterHorizontally).size(90.dp).background(Palette.Blue, CircleShape),
            contentAlignment = Alignment.Center,
        ) { Text("💧", fontSize = 39.sp) }
        Text(
            "Enter your\nblood glucose level",
            Modifier.align(Alignment.CenterHorizontally),
            color = Palette.Ink, fontSize = 23.sp, lineHeight = 27.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
        )
        SectionLabel("Glucose Level")
        OutlinedTextField(
            value = value,
            onValueChange = { candidate -> if (candidate.all { it in '0'..'9' }) value = candidate },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("120") },
            suffix = { Text("mg/dL") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(11.dp),
            colors = colors,
            isError = attemptedSave && parsedValue == null,
            supportingText = if (attemptedSave && parsedValue == null) {
                { Text("Enter a positive whole number") }
            } else null,
        )
        SectionLabel("Date & Time")
        GlucoseCard(
            Modifier.fillMaxWidth(),
            color = Palette.Field,
            outlined = true,
            onClick = {
                val selected = Calendar.getInstance().apply { timeInMillis = timestamp }
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        selected.set(year, month, day)
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                selected.set(Calendar.HOUR_OF_DAY, hour)
                                selected.set(Calendar.MINUTE, minute)
                                selected.set(Calendar.SECOND, 0)
                                selected.set(Calendar.MILLISECOND, 0)
                                timestamp = selected.timeInMillis
                            },
                            selected.get(Calendar.HOUR_OF_DAY), selected.get(Calendar.MINUTE), false,
                        ).show()
                    },
                    selected.get(Calendar.YEAR), selected.get(Calendar.MONTH), selected.get(Calendar.DAY_OF_MONTH),
                ).apply {
                    datePicker.maxDate = System.currentTimeMillis()
                }.show()
            },
        ) { SupportingText("📅  ${readingDateTime(timestamp)}") }
        if (attemptedSave && dateIsInFuture) {
            Text("Choose a date and time that has already passed.", color = Palette.Red, fontSize = 13.sp)
        }
        SectionLabel("Tags (optional)")
        tagOptions.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { (label, option) ->
                    FilterChip(
                        selected = tag == option,
                        onClick = { tag = if (tag == option) null else option },
                        label = { Text(label, fontSize = 12.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Palette.Sage, selectedContainerColor = Palette.LightGreen,
                        ),
                    )
                }
            }
        }
        SectionLabel("Notes (optional)")
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("e.g. how you felt, what you ate…", fontSize = 13.sp) },
            minLines = 2,
            shape = RoundedCornerShape(11.dp),
            colors = colors,
        )
        PrimaryGlucoseButton(
            text = if (existing == null) "Save Reading" else "Save Changes",
            onClick = {
                attemptedSave = true
                if (parsedValue != null && !dateIsInFuture) {
                    onSave(parsedValue, timestamp, tag, notes.trim())
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).widthIn(min = 132.dp),
        )
    }
}
