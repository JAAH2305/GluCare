package com.glucare.ui.screens

import androidx.compose.foundation.BorderStroke
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

private val DetailsGreen = Color(0xFF5F7F72)
private val DetailsLight = Color(0xFFE7EEE8)
private val DetailsBg = Color(0xFFF8FAF8)
private val DetailsDarkText = Color(0xFF26332C)

@Composable
fun ActivityDetailsScreen(
    activity: ActivityItem,
    onBackClick: () -> Unit = {},
    onEditClick: (ActivityItem) -> Unit = {},
    onDeleteClick: (ActivityItem) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DetailsBg)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "‹",
                color = DetailsDarkText,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 16.dp, top = 8.dp, bottom = 8.dp, start = 4.dp)
                    .clickable { onBackClick() }
            )

            Text(
                text = "Activity Details",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DetailsDarkText
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Center Icon & Title
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDDE3DF)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = activity.icon, fontSize = 34.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = activity.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DetailsDarkText
            )

            Text(
                text = "${activity.durationMinutes} min",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Summary 3-column Card (Calories, Steps, Heart Rate)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DetailsLight)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SummaryStatColumn(
                    icon = "🔥",
                    value = "${activity.calories}",
                    unit = "kcal",
                    label = "Calories Burned",
                    modifier = Modifier.weight(1f)
                )

                SummaryStatColumn(
                    icon = "👣",
                    value = "%,d".format(activity.steps),
                    unit = "",
                    label = "Steps\n(approx.)",
                    modifier = Modifier.weight(1f)
                )

                SummaryStatColumn(
                    icon = "💚",
                    value = activity.heartRate?.toString() ?: "-",
                    unit = "",
                    label = "Heart Rate\n(approx.)",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Date & Time
        Text(
            text = "📅  Date & Time",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = DetailsDarkText
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = activity.dateLabel,
                fontSize = 14.sp,
                color = DetailsDarkText
            )
            Text(
                text = activity.time,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 10.dp),
            color = Color(0xFFE5E5E5)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Notes
        Text(
            text = "📝  Notes (optional)",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = DetailsDarkText
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = activity.notes.ifEmpty { "No notes added." },
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Edit Activity Button
        Button(
            onClick = { onEditClick(activity) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DetailsGreen,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Edit Activity",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Delete Activity Outlined Button
        OutlinedButton(
            onClick = { onDeleteClick(activity) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            border = BorderStroke(1.dp, Color(0xFFD5DBD7)),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = DetailsDarkText
            )
        ) {
            Text(
                text = "🗑  Delete Activity",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun SummaryStatColumn(
    icon: String,
    value: String,
    unit: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = icon, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DetailsDarkText
        )
        if (unit.isNotEmpty()) {
            Text(
                text = unit,
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color.Gray,
            lineHeight = 13.sp
        )
    }
}