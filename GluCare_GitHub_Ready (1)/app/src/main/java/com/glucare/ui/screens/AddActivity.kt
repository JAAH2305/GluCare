package com.glucare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AddGreen = Color(0xFF5F7F72)
private val AddLight = Color(0xFFE7EEE8)
private val AddBackground = Color(0xFFFAFBF9)
private val AddDarkText = Color(0xFF263B33)

@Composable
fun AddActivityScreen(
    initialActivity: ActivityItem? = null,
    onBackClick: () -> Unit = {},
    onSaveActivity: (ActivityItem) -> Unit = {}
) {
    val activityOptions = listOf(
        Triple("Walking", "🚶", 4),
        Triple("Running", "🏃", 8),
        Triple("Cycling", "🚴", 6),
        Triple("Weight Training", "🏋️", 5)
    )

    var selectedType by remember {
        mutableStateOf(initialActivity?.name ?: "Walking")
    }
    var duration by remember {
        mutableStateOf(initialActivity?.durationMinutes?.toString() ?: "30")
    }
    var calories by remember {
        mutableStateOf(initialActivity?.calories?.toString() ?: "120")
    }
    var dateTime by remember {
        mutableStateOf(
            if (initialActivity != null) "${initialActivity.dateLabel}  ${initialActivity.time}"
            else "Nov 21, 2026  5:30 PM"
        )
    }
    var notes by remember {
        mutableStateOf(initialActivity?.notes ?: "")
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AddBackground)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "‹",
                color = AddDarkText,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 16.dp, top = 8.dp, bottom = 8.dp, start = 4.dp)
                    .clickable { onBackClick() }
            )

            Text(
                text = if (initialActivity == null) "Add Activity" else "Edit Activity",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = AddDarkText
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Activity Type",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = AddDarkText
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 2x2 Grid matching design
        val rows = activityOptions.chunked(2)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { (typeName, icon, kcalPerMin) ->
                    val isSelected = selectedType == typeName
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(74.dp)
                            .clickable {
                                selectedType = typeName
                                val mins = duration.toIntOrNull()
                                if (mins != null && mins > 0 && initialActivity == null) {
                                    calories = (mins * kcalPerMin).toString()
                                }
                            },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) AddGreen else Color(0xFFE2E6E3)
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) AddLight else Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = icon, fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = typeName,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = AddDarkText
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Duration
        Text(
            text = "🕒  Duration",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = AddDarkText
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = duration,
            onValueChange = { newVal ->
                duration = newVal.filter { it.isDigit() }
                errorMessage = null
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("30", color = Color.Gray) },
            trailingIcon = {
                Text(
                    text = "min  ›",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = AddGreen,
                unfocusedBorderColor = Color(0xFFE2E6E3)
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Calories Burned
        Text(
            text = "🔥  Calories Burned",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = AddDarkText
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = calories,
            onValueChange = { newVal ->
                calories = newVal.filter { it.isDigit() }
                errorMessage = null
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("120", color = Color.Gray) },
            trailingIcon = {
                Text(
                    text = "kcal  ›",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = AddGreen,
                unfocusedBorderColor = Color(0xFFE2E6E3)
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Date & Time
        Text(
            text = "📅  Date & Time",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = AddDarkText
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = dateTime,
            onValueChange = { dateTime = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = AddGreen,
                unfocusedBorderColor = Color(0xFFE2E6E3)
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Notes (optional)
        Text(
            text = "📝  Notes (optional)",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = AddDarkText
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp),
            placeholder = { Text("Evening walk", color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = AddGreen,
                unfocusedBorderColor = Color(0xFFE2E6E3)
            )
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Save Button
        Button(
            onClick = {
                val parsedDuration = duration.toIntOrNull()
                val parsedCalories = calories.toIntOrNull()
                if (parsedDuration == null || parsedDuration <= 0 || parsedCalories == null || parsedCalories <= 0) {
                    errorMessage = "Please enter valid duration and calories."
                    return@Button
                }

                val matchedIcon = activityOptions.firstOrNull { it.first == selectedType }?.second ?: "🚶"
                val cleanDateTime = dateTime.trim().ifEmpty { "Today  5:30 PM" }
                val parts = cleanDateTime.split(Regex("\\s{2,}|\\s+(?=\\d{1,2}:\\d{2})"), limit = 2)
                val datePart = parts.firstOrNull()?.trim().orEmpty().ifEmpty { "Today" }
                val timePart = parts.getOrNull(1)?.trim().orEmpty().ifEmpty { "5:30 PM" }

                val savedItem = ActivityItem(
                    id = initialActivity?.id ?: System.currentTimeMillis(),
                    name = selectedType,
                    icon = matchedIcon,
                    durationMinutes = parsedDuration,
                    calories = parsedCalories,
                    steps = initialActivity?.steps ?: (parsedDuration * 110),
                    heartRate = initialActivity?.heartRate,
                    dateLabel = datePart,
                    time = timePart,
                    notes = notes.trim().ifEmpty { "Great job! Keep moving and stay active." }
                )
                onSaveActivity(savedItem)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AddGreen,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Save Activity",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}