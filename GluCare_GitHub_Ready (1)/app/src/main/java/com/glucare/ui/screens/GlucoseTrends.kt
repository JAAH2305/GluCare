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

@Composable
fun GlucoseTrends() {

    var selectedRange by remember {
        mutableStateOf("7 Days")
    }

    val green = Color(0xFF5F7F72)
    val pale = Color(0xFFE7EEE8)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {

        Text(
            "Glucose Trends",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Track your glucose patterns over time.",
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(22.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(pale, RoundedCornerShape(20.dp))
                .padding(4.dp)
        ) {

            listOf("7 Days", "30 Days", "3 Months").forEach { range ->

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (selectedRange == range) green
                            else Color.Transparent,
                            RoundedCornerShape(20.dp)
                        )
                        .clickable {
                            selectedRange = range
                        }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        range,
                        fontSize = 11.sp,
                        color = if (selectedRange == range)
                            Color.White else Color.DarkGray
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(pale, RoundedCornerShape(12.dp))
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text("☺", fontSize = 28.sp, color = green)

            Spacer(Modifier.width(12.dp))

            Column {
                Text(
                    "Your glucose is stable",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Keep up the good work!",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(Modifier.height(22.dp))

        Text(
            "Glucose Levels",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(pale, RoundedCornerShape(15.dp))
                .padding(15.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                "Glucose Chart",
                color = green,
                fontSize = 15.sp
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {

            TrendCard(
                "Within Range",
                "78%",
                green,
                Modifier.weight(1f)
            )

            TrendCard(
                "Above Range",
                "15%",
                Color(0xFFA54B55),
                Modifier.weight(1f)
            )

            TrendCard(
                "Below Range",
                "7%",
                Color(0xFFB78235),
                Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TrendCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .background(
                Color(0xFFE7EEE8),
                RoundedCornerShape(10.dp)
            )
            .padding(9.dp)
    ) {

        Text(
            "● $title",
            fontSize = 9.sp,
            color = color
        )

        Spacer(Modifier.height(7.dp))

        Text(
            value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

