package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.theme.GluCareTheme

private val ScheduleBg = Color(0xFFFAFBF9)
private val ScheduleGreen = Color(0xFF5F7F72)
private val ScheduleLightGreen = Color(0xFFEDF2E8)
private val ScheduleText = Color(0xFF30372E)
private val ScheduleGray = Color(0xFF999999)
private val ScheduleBorder = Color(0xFFE8EBE7)
private val ScheduleOrange = Color(0xFFB78235)

private data class MedicationDose(
    val name: String,
    val dosage: String,
    val time: String,
    val period: String,
    var taken: Boolean = false
)

@Composable
fun MedicationScheduleScreen(onBack: () -> Unit = {}) {

    var selectedDay by remember { mutableIntStateOf(21) }
    var linkWithGlucose by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }

    val doses = remember {
        mutableStateListOf(
            MedicationDose(
                "Metformin",
                "500 mg • 1 Tablet",
                "08:00 AM",
                "Morning",
                true
            ),
            MedicationDose(
                "Metformin",
                "500 mg • 1 Tablet",
                "02:00 PM",
                "Afternoon",
                false
            ),
            MedicationDose(
                "Insulin",
                "10 Units",
                "08:00 PM",
                "Evening",
                false
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScheduleBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp)
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "‹",
                    fontSize = 28.sp,
                    color = ScheduleGreen
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Medication Schedule",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ScheduleText
                )

                Text(
                    text = "Keep track of your daily doses",
                    fontSize = 12.sp,
                    color = ScheduleGray
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Calendar Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "November 2026",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ScheduleText
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Select a date to view your plan",
                    fontSize = 11.sp,
                    color = ScheduleGray
                )
            }

            Text(
                text = "▦",
                fontSize = 25.sp,
                color = ScheduleGreen
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Days Selector
        val days = listOf(
            "Sun" to 15,
            "Mon" to 16,
            "Tue" to 17,
            "Wed" to 18,
            "Thu" to 19,
            "Fri" to 20,
            "Sat" to 21
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(days) { day ->
                val isSelected = selectedDay == day.second

                Column(
                    modifier = Modifier
                        .width(43.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            if (isSelected) ScheduleGreen
                            else Color.White
                        )
                        .clickable {
                            selectedDay = day.second
                        }
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = day.first,
                        fontSize = 10.sp,
                        color = if (isSelected) Color.White
                        else ScheduleGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = day.second.toString(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White
                        else ScheduleText
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) Color.White
                                else ScheduleGreen.copy(alpha = 0.6f)
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Today's Plan
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Today's Plan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ScheduleText
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Your scheduled medications",
                    fontSize = 11.sp,
                    color = ScheduleGray
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(ScheduleLightGreen)
                    .padding(horizontal = 11.dp, vertical = 7.dp)
            ) {
                Text(
                    text = "${doses.count { it.taken }}/${doses.size} Taken",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ScheduleGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Dose Cards
        val periods = listOf("Morning", "Afternoon", "Evening")

        periods.forEach { period ->

            val periodDoses = doses.filter { it.period == period }

            if (periodDoses.isNotEmpty()) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (period) {
                            "Morning" -> "☀"
                            "Afternoon" -> "◉"
                            else -> "☾"
                        },
                        fontSize = 19.sp,
                        color = ScheduleGreen
                    )

                    Spacer(modifier = Modifier.width(9.dp))

                    Text(
                        text = period,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ScheduleText
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                periodDoses.forEach { dose ->

                    val index = doses.indexOf(dose)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(17.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 1.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(13.dp))
                                    .background(ScheduleLightGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "💊",
                                    fontSize = 22.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(13.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = dose.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ScheduleText
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = dose.dosage,
                                    fontSize = 11.sp,
                                    color = ScheduleGray
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "◷  ${dose.time}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = ScheduleGreen
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Checkbox(
                                    checked = dose.taken,
                                    onCheckedChange = {
                                        doses[index] = dose.copy(taken = it)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = ScheduleGreen,
                                        uncheckedColor = ScheduleBorder
                                    )
                                )

                                Text(
                                    text = if (dose.taken) "Taken"
                                    else "Mark taken",
                                    fontSize = 9.sp,
                                    color = if (dose.taken)
                                        ScheduleGreen
                                    else ScheduleGray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(13.dp))
            }
        }

        // Glucose Link Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = ScheduleLightGreen
            ),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clickable(onClick = onBack)
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "♡",
                        fontSize = 23.sp,
                        color = ScheduleGreen
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Link dose with glucose reading",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ScheduleText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Connect your medication to a glucose record",
                        fontSize = 10.sp,
                        color = ScheduleGray
                    )
                }

                Switch(
                    checked = linkWithGlucose,
                    onCheckedChange = {
                        linkWithGlucose = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = ScheduleGreen
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Add Dose Button
        OutlinedButton(
            onClick = {
                showAddDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = ScheduleGreen
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                ScheduleGreen
            )
        ) {
            Text(
                text = "+",
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Add another dose",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(28.dp))
    }

    // Add Dose Dialog
    if (showAddDialog) {

        var medicationName by remember { mutableStateOf("") }
        var dosage by remember { mutableStateOf("") }
        var time by remember { mutableStateOf("09:00 AM") }
        var period by remember { mutableStateOf("Morning") }

        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
            },
            title = {
                Text(
                    text = "Add New Dose",
                    fontWeight = FontWeight.Bold,
                    color = ScheduleText
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = medicationName,
                        onValueChange = {
                            medicationName = it
                        },
                        label = { Text("Medication name") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = dosage,
                        onValueChange = {
                            dosage = it
                        },
                        label = { Text("Dosage") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = time,
                        onValueChange = {
                            time = it
                        },
                        label = { Text("Time") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Period",
                        fontSize = 12.sp,
                        color = ScheduleGray
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        listOf(
                            "Morning",
                            "Afternoon",
                            "Evening"
                        ).forEach { option ->

                            FilterChip(
                                selected = period == option,
                                onClick = {
                                    period = option
                                },
                                label = {
                                    Text(
                                        text = option,
                                        fontSize = 10.sp
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ScheduleLightGreen,
                                    selectedLabelColor = ScheduleGreen
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (
                            medicationName.isNotBlank() &&
                            dosage.isNotBlank() &&
                            time.isNotBlank()
                        ) {
                            doses.add(
                                MedicationDose(
                                    medicationName,
                                    dosage,
                                    time,
                                    period
                                )
                            )
                            showAddDialog = false
                        }
                    }
                ) {
                    Text(
                        text = "Add",
                        color = ScheduleGreen
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddDialog = false
                    }
                ) {
                    Text(
                        text = "Cancel",
                        color = ScheduleGray
                    )
                }
            },
            containerColor = Color.White
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    widthDp = 393,
    heightDp = 852
)
@Composable
fun MedicationScheduleScreenPreview() {
    GluCareTheme {
        MedicationScheduleScreen()
    }
}