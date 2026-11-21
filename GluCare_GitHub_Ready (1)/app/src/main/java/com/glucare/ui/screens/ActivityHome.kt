package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ActivityGreen = Color(0xFF5F7F72)
private val ActivityDarkText = Color(0xFF263B33)
private val ActivityLight = Color(0xFFE7EEE8)
private val ActivityBg = Color(0xFFFAFBF9)

@Composable
fun ActivityHomeScreen(
    activities: List<ActivityItem>,
    totalSteps: Int,
    totalDurationMin: Int,
    totalCalories: Int,
    selectedBottomTab: Int = 0,
    onSelectBottomTab: (Int) -> Unit = {},
    onViewHistoryClick: () -> Unit = {},
    onActivityClick: (ActivityItem) -> Unit = {},
    onAddActivityClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = ActivityBg,
        bottomBar = {
            GluCareBottomBar(
                selectedIndex = selectedBottomTab,
                onSelect = onSelectBottomTab
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ActivityBg)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(ActivityLight),
                contentAlignment = Alignment.Center
            ) {
                Text("🌱", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Good morning!",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = ActivityDarkText
            )

            Text(
                text = "Keep moving, it's good for your health",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Today's Activity",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = ActivityDarkText
                        )

                        Text(
                            text = "View History ›",
                            color = ActivityGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onViewHistoryClick() }
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ActivityStat(
                            emoji = "👣",
                            value = "%,d".format(totalSteps),
                            label = "Steps",
                            modifier = Modifier.weight(1f)
                        )
                        ActivityStat(
                            emoji = "🕒",
                            value = "$totalDurationMin min",
                            label = "Duration",
                            modifier = Modifier.weight(1f)
                        )
                        ActivityStat(
                            emoji = "🔥",
                            value = "$totalCalories",
                            label = "kcal Burned",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Recent Activities",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = ActivityDarkText
            )

            Spacer(modifier = Modifier.height(10.dp))

            activities.take(3).forEach { item ->
                ActivityRow(
                    item = item,
                    onClick = { onActivityClick(item) }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onAddActivityClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ActivityGreen,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "+  Add Activity",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = ActivityLight),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFCBDAD0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("☺", fontSize = 18.sp, color = ActivityDarkText)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Small steps",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = ActivityDarkText
                        )
                        Text(
                            text = "make a big difference!",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ActivityStat(
    emoji: String,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(105.dp),
        colors = CardDefaults.cardColors(containerColor = ActivityLight),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ActivityDarkText
            )
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
private fun ActivityRow(
    item: ActivityItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(13.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ActivityLight),
                contentAlignment = Alignment.Center
            ) {
                Text(item.icon, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = ActivityDarkText
                )
                Text(
                    text = "${item.durationMinutes} min  •  ${item.calories} kcal",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = item.time,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                Text(
                    text = item.dateLabel,
                    fontSize = 9.sp,
                    color = ActivityGreen
                )
            }

            Text(
                text = "  ›",
                color = ActivityGreen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun GluCareBottomBar(
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    data class NavItem(val label: String, val symbol: String)

    val items = listOf(
        NavItem("Home", "🏠"),
        NavItem("Glucose", "🩸"),
        NavItem("Medication", "💊"),
        NavItem("Meals", "🍽"),
        NavItem("More", "•••")
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 2.dp
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedIndex == index
            NavigationBarItem(
                selected = selected,
                onClick = { onSelect(index) },
                icon = {
                    Text(
                        text = item.symbol,
                        fontSize = 16.sp
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ActivityDarkText,
                    selectedTextColor = ActivityDarkText,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = ActivityLight
                )
            )
        }
    }
}