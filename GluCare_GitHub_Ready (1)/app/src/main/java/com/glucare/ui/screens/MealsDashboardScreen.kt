package com.glucare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.RestaurantMenu

private val PrimarySage = Color(0xFF5F7F72)
private val LightSageCard = Color(0xFFE7EEE8)
private val BgOffWhite = Color(0xFFF9FAF8)

data class DailyMealUiItem(
    val id: Long,
    val category: String,
    val description: String,
    val isLogged: Boolean,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsDashboardScreen(
    onBackClick: () -> Unit = {},
    onNavigateToAddMeal: () -> Unit = {},
    onNavigateToMealDetails: (Long) -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    bottomBar: @Composable () -> Unit = {}
) {
    val sampleMeals = remember {
        listOf(
            DailyMealUiItem(1L, "Breakfast", "Oatmeal with berries", true, Icons.Default.BreakfastDining),
            DailyMealUiItem(2L, "Lunch", "Grilled chicken & salad", true, Icons.Default.LunchDining),
            DailyMealUiItem(3L, "Dinner", "Not logged yet", false, Icons.Default.DinnerDining),
            DailyMealUiItem(4L, "Snacks", "Green apple slices", true, Icons.Default.Icecream)
        )
    }

    Scaffold(
        containerColor = BgOffWhite,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meals Tracking",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF222B27)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    FilterChip(
                        selected = true,
                        onClick = onNavigateToHistory,
                        label = { Text("Today", fontSize = 12.sp) },
                        trailingIcon = {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "History", modifier = Modifier.size(16.dp))
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = LightSageCard,
                            selectedLabelColor = PrimarySage
                        ),
                        modifier = Modifier.padding(end = 12.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgOffWhite)
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
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = LightSageCard),
                    border = BorderStroke(1.dp, PrimarySage.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestaurantMenu,
                            contentDescription = null,
                            tint = PrimarySage,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Daily Diet Overview", fontSize = 13.sp, color = Color(0xFF4A5650))
                            Text("Balanced Diet", fontSize = 14.sp, color = Color(0xFF4A5650))
                            Text(
                                text = "2 of 3 Logged",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF222B27)
                            )
                        }
                    }
                }
            }

            items(sampleMeals) { meal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (meal.isLogged) onNavigateToMealDetails(meal.id)
                            else onNavigateToAddMeal()
                        },
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
                                contentDescription = meal.category,
                                tint = Color(0xFF222B27),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = meal.category,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimarySage
                                )
                                Text(
                                    text = meal.description,
                                    fontSize = 13.sp,
                                    color = if (meal.isLogged) Color(0xFF222B27) else Color.Gray
                                )
                            }
                        }
                        Icon(
                            imageVector = if (meal.isLogged) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = if (meal.isLogged) "Logged" else "Not logged",
                            tint = if (meal.isLogged) PrimarySage else Color(0xFF4A5650)
                        )
                    }
                }
            }

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