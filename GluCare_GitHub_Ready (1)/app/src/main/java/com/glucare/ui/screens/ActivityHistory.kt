package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HistoryGreen = Color(0xFF5F7F72)
private val HistoryLight = Color(0xFFE7EEE8)
private val HistoryBg = Color(0xFFF8FAF8)
private val HistoryDarkText = Color(0xFF26332C)

@Composable
fun ActivityHistoryScreen(
    activities: List<ActivityItem>,
    selectedBottomTab: Int = 0,
    onSelectBottomTab: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    onActivityClick: (ActivityItem) -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf("Today") }
    val filters = listOf("Today", "Week", "Month")

    val filteredActivities = remember(activities, selectedFilter) {
        when (selectedFilter) {
            "Today" -> activities.filter { it.dateLabel.equals("Today", ignoreCase = true) }
                .ifEmpty { activities }
            "Week" -> activities
            else -> activities
        }
    }

    val groupedByDate = remember(filteredActivities) {
        filteredActivities.groupBy { it.dateLabel }
    }

    Scaffold(
        containerColor = HistoryBg,
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
                .background(HistoryBg)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "‹",
                    color = HistoryDarkText,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 16.dp, top = 8.dp, bottom = 8.dp, start = 4.dp)
                        .clickable { onBackClick() }
                )

                Text(
                    text = "Activity History",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = HistoryDarkText
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Filter Pills: Today | Week | Month
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(34.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) HistoryGreen else Color.White)
                            .clickable { selectedFilter = filter },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) Color.White else HistoryDarkText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Grouped List by Date
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                groupedByDate.forEach { (dateHeader, dayItems) ->
                    item(key = "header_$dateHeader") {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp, bottom = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = dateHeader,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = HistoryDarkText
                            )
                            Text(
                                text = dayItems.firstOrNull()?.fullDateSubtitle ?: "",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    items(dayItems, key = { it.id }) { activity ->
                        ActivityHistoryItem(
                            activity = activity,
                            onClick = { onActivityClick(activity) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityHistoryItem(
    activity: ActivityItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(HistoryLight),
                contentAlignment = Alignment.Center
            ) {
                Text(text = activity.icon, fontSize = 17.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = HistoryDarkText
                )
                Text(
                    text = "${activity.durationMinutes} min  •  ${activity.calories} kcal",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }

            Text(
                text = activity.time,
                color = Color.Gray,
                fontSize = 11.sp
            )

            Text(
                text = "  ›",
                color = HistoryGreen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}