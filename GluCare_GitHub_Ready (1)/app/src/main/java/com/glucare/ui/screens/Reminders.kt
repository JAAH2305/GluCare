package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private val green = Color(0xFF5F7F72)
private val pale = Color(0xFFE7EEE8)
private val dark = Color(0xFF26332E)

@Composable
fun Reminders(dataStore: GluCareDataStore) {

    var showAddScreen by remember { mutableStateOf(false) }

    val reminders by dataStore.reminders.collectAsState(
        initial = listOf(
            ReminderItem(1, "💧", "Check Blood Glucose", "12:00 PM", "Every day"),
            ReminderItem(2, "💊", "Take Medication", "12:00 PM", "Every day"),
            ReminderItem(3, "🍴", "Log Your Meal", "12:00 PM", "Every day"),
            ReminderItem(4, "🏃", "Physical Activity", "12:00 PM", "Every day")
        )
    )

    val scope = rememberCoroutineScope()

    if (showAddScreen) {

        AddGlucoseReminder(
            onBack = { showAddScreen = false },
            onSave = { newReminder ->

                scope.launch {
                    val updatedReminders = reminders + newReminder
                    dataStore.saveReminders(updatedReminders)
                    showAddScreen = false
                }
            }
        )

    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp)
        ) {

            Text(
                "Reminders",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = dark
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Stay on track with your health goals.",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(25.dp))

            reminders.forEach { reminder ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(pale, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(reminder.icon, fontSize = 24.sp)

                    Spacer(Modifier.width(12.dp))

                    Column(Modifier.weight(1f)) {

                        Text(
                            reminder.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            reminder.time,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )

                        Text(
                            if (reminder.repeat == "Every week" &&
                                reminder.days.isNotBlank()
                            ) reminder.days else reminder.repeat,
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }

                    Switch(
                        checked = reminder.enabled,
                        onCheckedChange = { isEnabled ->

                            scope.launch {
                                val updated = reminders.map {
                                    if (it.id == reminder.id) {
                                        it.copy(enabled = isEnabled)
                                    } else {
                                        it
                                    }
                                }

                                dataStore.saveReminders(updated)
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = green
                        )
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { showAddScreen = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = green
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("+  Add Reminder", fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun AddGlucoseReminder(
    onBack: () -> Unit,
    onSave: (ReminderItem) -> Unit
) {

    var reminderName by remember {
        mutableStateOf("Check Blood Glucose")
    }

    var selectedTime by remember {
        mutableStateOf("12:00 PM")
    }

    var repeat by remember {
        mutableStateOf("Every day")
    }

    var selectedDays by remember {
        mutableStateOf(setOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"))
    }

    var reminderBefore by remember {
        mutableStateOf("10 minutes before")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text("←", fontSize = 30.sp, color = dark)
            }

            Spacer(Modifier.width(12.dp))

            Text("◷", fontSize = 22.sp)

            Spacer(Modifier.width(8.dp))

            Text(
                "Add Glucose Reminder",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(5.dp))

        Text(
            "Set a reminder to check your blood glucose.",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(18.dp))

        Text("Reminder Name", fontSize = 12.sp)

        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = reminderName,
            onValueChange = { reminderName = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(Modifier.height(14.dp))

        Text("Time", fontSize = 12.sp)

        ChoiceRow(
            value = selectedTime,
            options = listOf(
                "8:00 AM",
                "9:00 AM",
                "12:00 PM",
                "3:00 PM",
                "6:00 PM",
                "9:00 PM"
            ),
            onSelect = { selectedTime = it }
        )

        Spacer(Modifier.height(12.dp))

        Text("Repeat", fontSize = 12.sp)

        ChoiceRow(
            value = repeat,
            options = listOf("Every day", "Every week", "Once"),
            onSelect = { repeat = it }
        )

        Spacer(Modifier.height(12.dp))

        Text("Days", fontSize = 12.sp)

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                .forEach { day ->

                    val selected = selectedDays.contains(day)

                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .background(
                                if (selected) green else pale,
                                RoundedCornerShape(50)
                            )
                            .clickable {
                                selectedDays =
                                    if (selected) selectedDays - day
                                    else selectedDays + day
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            day.take(1),
                            fontSize = 11.sp,
                            color = if (selected) Color.White else dark
                        )
                    }
                }
        }

        Spacer(Modifier.height(14.dp))

        Text("Reminder Before", fontSize = 12.sp)

        ChoiceRow(
            value = reminderBefore,
            options = listOf(
                "At time of reminder",
                "5 minutes before",
                "10 minutes before",
                "15 minutes before",
                "30 minutes before"
            ),
            onSelect = { reminderBefore = it }
        )

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = {

                if (reminderName.isNotBlank()) {

                    onSave(
                        ReminderItem(
                            id = System.currentTimeMillis().toInt(),
                            icon = "🔔",
                            name = reminderName,
                            time = selectedTime,
                            repeat = repeat,
                            days = selectedDays.sorted().joinToString(", "),
                            reminderBefore = reminderBefore,
                            enabled = true
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = green
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Save Reminder", fontSize = 14.sp)
        }
    }
}

@Composable
fun ChoiceRow(
    value: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Box {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .background(pale, RoundedCornerShape(10.dp))
                .clickable { expanded = true }
                .padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text("◷", fontSize = 15.sp)

            Spacer(Modifier.width(10.dp))

            Text(
                value,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )

            Text("›", fontSize = 20.sp)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            options.forEach { option ->

                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}