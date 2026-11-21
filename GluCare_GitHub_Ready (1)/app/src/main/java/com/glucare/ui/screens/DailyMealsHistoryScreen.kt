package com.glucare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun DailyMealsHistoryScreen(
    onBackClick: () -> Unit = {},
    onNavigateToAddMeal: () -> Unit = {},
    onNavigateToMealDetails: (Long) -> Unit = {},
    bottomBar: @Composable () -> Unit = {}
) {
    var selectedDay by remember { mutableStateOf(9) }
    val weekDays = listOf(
        "Mon" to 5, "Tue" to 6, "Wed" to 7,
        "Thu" to 8, "Fri" to 9, "Sat" to 10
    )

    val historyMeals = remember {
        listOf(
            DailyMealUiItem(1L, "Breakfast", "Oatmeal with berries", true, Icons.Default.Star),
            DailyMealUiItem(2L, "Lunch", "Grilled chicken & salad", true, Icons.Default.FavoriteBorder),
            DailyMealUiItem(4L, "Snacks", "Green apple slices", true, Icons.Default.Notifications)
        )
    }

    Scaffold(
        containerColor = BgOffWhite,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Meals History",
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
        },
        bottomBar = bottomBar
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Horizontal Date Selector Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE0E5E2))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        weekDays.forEach { (dayName, dayNum) ->
                            val isSelected = selectedDay == dayNum
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clickable { selectedDay = dayNum }
                                    .padding(4.dp)
                            ) {
                                Text(dayName, fontSize = 12.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(6.dp))
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) LightSageCard else Color.Transparent),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = dayNum.toString(),
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) PrimarySage else Color(0xFF222B27)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 2. Selected Day Header
            item {
                Text(
                    text = "Today, Oct $selectedDay",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF222B27)
                )
            }

            // 3. Logged Meals List for Selected Date
            items(historyMeals) { meal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToMealDetails(meal.id) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = meal.icon,
                                contentDescription = null,
                                tint = PrimarySage,
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = meal.category,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = PrimarySage
                                )
                                Text(
                                    text = meal.description,
                                    fontSize = 13.sp,
                                    color = Color(0xFF222B27)
                                )
                            }
                        }
                        Icon(Icons.Default.Check, contentDescription = "Logged", tint = PrimarySage)
                    }
                }
            }

            // 4. Previous Day Summary Card
            item {
                Text(
                    text = "Previous Day, Oct ${selectedDay - 1}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF222B27)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE0E5E2))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("3 Meals recorded", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text("Diet goals met", fontSize = 12.sp, color = Color.Gray)
                        }
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Gray)
                    }
                }
            }

            // 5. + Log New Meal Button
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onNavigateToAddMeal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimarySage)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Log New Meal", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}