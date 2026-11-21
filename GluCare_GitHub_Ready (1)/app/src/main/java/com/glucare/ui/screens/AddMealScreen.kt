package com.glucare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimarySage = Color(0xFF5F7F72)
private val LightSageCard = Color(0xFFE7EEE8)
private val BgOffWhite = Color(0xFFF9FAF8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMealScreen(
    mealId: Long? = null,
    onBackClick: () -> Unit = {},
    onSaveMealSuccess: (Long) -> Unit = {}
) {
    var selectedType by remember { mutableStateOf("Breakfast") }
    var description by remember { mutableStateOf(if (mealId != null) "Grilled Chicken Salad" else "") }
    var selectedPortion by remember { mutableStateOf("Small") }

    val mealTypes = listOf("Breakfast", "Lunch", "Dinner", "Snack")
    val portions = listOf("Small", "Medium", "Large")

    Scaffold(
        containerColor = BgOffWhite,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (mealId == null) "Add Meal" else "Edit Meal",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222B27)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BgOffWhite)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Meal Type Selector (2x2 Grid)
            Text(
                text = "Meal Type Selector",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color(0xFF222B27)
            )
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                mealTypes.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { type ->
                            val isSelected = selectedType == type
                            OutlinedButton(
                                onClick = { selectedType = type },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (isSelected) LightSageCard else Color.White,
                                    contentColor = if (isSelected) PrimarySage else Color(0xFF222B27)
                                ),
                                border = BorderStroke(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = if (isSelected) PrimarySage else Color.LightGray
                                )
                            ) {
                                Text(
                                    text = type,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            // 2. Meal Description Input
            Text(
                text = "Meal Description",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color(0xFF222B27)
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("e.g., Oatmeal with berries", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = PrimarySage
                ),
                singleLine = true
            )

            // 3. Portion Estimation (Small / Medium / Large)
            Text(
                text = "Portion Estimation",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color(0xFF222B27)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                portions.forEach { portion ->
                    val isSelected = selectedPortion == portion
                    OutlinedButton(
                        onClick = { selectedPortion = portion },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) LightSageCard else Color.White,
                            contentColor = if (isSelected) PrimarySage else Color(0xFF222B27)
                        ),
                        border = BorderStroke(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) PrimarySage else Color.LightGray
                        )
                    ) {
                        Text(
                            text = portion,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            // 4. Time Logged Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE0E5E2))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Time Logged",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF222B27)
                    )
                    Text(
                        text = "Current time auto-filled",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "e.g. Oct 10, 2026 - 1:30 PM",
                        fontSize = 12.sp,
                        color = Color(0xFF4A5650)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 5. Save Meal Button
            Button(
                onClick = { onSaveMealSuccess(mealId ?: 1L) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimarySage)
            ) {
                Text("Save Meal", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}