
package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

private val ReminderBg = Color(0xFFFAFBF9)
private val ReminderGreen = Color(0xFF5F7F72)
private val ReminderText = Color(0xFF30372E)
private val ReminderGray = Color(0xFF999999)
private val ReminderBorder = Color(0xFFE8EBE7)
private val ReminderLightGreen = Color(0xFFEDF2E8)

@Composable
fun MedicationReminderScreen(onBack: () -> Unit = {}, onSave: () -> Unit = {}) {

    var selectedTime by remember { mutableStateOf("08:00 AM") }
    var selectedFrequency by remember { mutableStateOf("Every day") }
    var reminderEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ReminderBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "‹",
                modifier = Modifier.clickable(onClick = onBack),
                fontSize = 30.sp,
                color = ReminderGray
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Medication Reminder",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ReminderText
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(ReminderLightGreen)
                .padding(vertical = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🔔",
                        fontSize = 30.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Stay on track!",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = ReminderText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Never miss your medication",
                    fontSize = 13.sp,
                    color = ReminderGray
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Medication",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = ReminderText
        )

        Spacer(modifier = Modifier.height(10.dp))

        ReminderCard {
            Column {
                Text(
                    text = "Metformin",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ReminderText
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "500 mg  •  Tablet",
                    fontSize = 12.sp,
                    color = ReminderGray
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Reminder Time",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = ReminderText
        )

        Spacer(modifier = Modifier.height(10.dp))

        ReminderCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Time",
                        fontSize = 12.sp,
                        color = ReminderGray
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = selectedTime,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ReminderGreen
                    )
                }

                Text(
                    text = "◷",
                    fontSize = 27.sp,
                    color = ReminderGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Repeat",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = ReminderText
        )

        Spacer(modifier = Modifier.height(10.dp))

        ReminderCard {
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = selectedFrequency,
                        fontSize = 14.sp,
                        color = ReminderText
                    )

                    Text(
                        text = "⌄",
                        fontSize = 20.sp,
                        color = ReminderGray
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf(
                        "Every day",
                        "Weekdays",
                        "Weekends"
                    ).forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedFrequency = option
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Notification Settings",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = ReminderText
        )

        Spacer(modifier = Modifier.height(10.dp))

        ReminderCard {
            Column {
                ReminderSwitchRow(
                    title = "Medication reminder",
                    subtitle = "Receive a notification at the selected time",
                    checked = reminderEnabled,
                    onCheckedChange = { reminderEnabled = it }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 13.dp),
                    color = ReminderBorder
                )

                ReminderSwitchRow(
                    title = "Sound",
                    subtitle = "Play a sound when reminder arrives",
                    checked = soundEnabled,
                    onCheckedChange = { soundEnabled = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ReminderGreen
            )
        ) {
            Text(
                text = "Save Reminder",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun ReminderCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .padding(16.dp),
        content = content
    )
}

@Composable
private fun ReminderSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = ReminderText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = ReminderGray
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = ReminderGreen
            )
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
fun MedicationReminderScreenPreview() {
    GluCareTheme {
        MedicationReminderScreen()
    }
}
