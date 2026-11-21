package com.glucare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimarySage = Color(0xFF5F7F72)
private val LightSageCard = Color(0xFFE7EEE8)
private val BgOffWhite = Color(0xFFF9FAF8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailsScreen(
    mealId: Long = 1L,
    onBackClick: () -> Unit = {},
    onEditClick: (Long) -> Unit = {},
    onDoneClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = BgOffWhite,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Meal Details",
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
                actions = {
                    IconButton(onClick = { onEditClick(mealId) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Meal", tint = Color(0xFF222B27))
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
            // 1. Meal Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE0E5E2))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(LightSageCard),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = PrimarySage)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Grilled Chicken Salad",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222B27)
                    )
                    Text(
                        text = "1:30 PM - Lunch",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            // 2. Nutritional Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE0E5E2))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Nutritional Summary",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF222B27)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFDF3E6)
                        ) {
                            Text(
                                text = "• Moderate Carbs",
                                color = Color(0xFFB78235),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFECEFED)
                        ) {
                            Text(
                                text = "• High Protein",
                                color = Color(0xFF4A5650),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // 3. Glucose Impact Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE0E5E2))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Glucose Impact",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF222B27)
                    )
                    Text(
                        text = "Related post-meal glucose",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "120 mg/dl",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222B27)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "● In range",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimarySage
                    )
                    Text(
                        text = "Excellent glucose response",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 4. Bottom Action Buttons (Edit Meal & Done)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { onEditClick(mealId) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFE4E7E5),
                        contentColor = Color(0xFF222B27)
                    ),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Text("Edit Meal", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                }

                Button(
                    onClick = onDoneClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimarySage)
                ) {
                    Text("Done", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                }
            }
        }
    }
}