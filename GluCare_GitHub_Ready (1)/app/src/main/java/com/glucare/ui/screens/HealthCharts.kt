package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AppGreen = Color(0xFF5F7F72)
private val AppPale = Color(0xFFE7EEE8)

@Composable
fun HealthCharts() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {

        Text(
            text = "Health Charts",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your health overview at a glance.",
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            HealthCard(
                "Average Glucose",
                "120",
                Modifier.weight(1f)
            )

            HealthCard(
                "Highest Reading",
                "145",
                Modifier.weight(1f)
            )

            HealthCard(
                "Lowest Reading",
                "82",
                Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Glucose This Week",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(
                    AppPale,
                    RoundedCornerShape(15.dp)
                )
                .padding(15.dp)
        ) {

            Text(
                text = "Glucose Chart",
                color = AppGreen,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    AppPale,
                    RoundedCornerShape(12.dp)
                )
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Weekly Average",
                fontSize = 14.sp
            )

            Text(
                text = "118 mg/dL",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = AppGreen
            )
        }
    }
}

@Composable
fun HealthCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .background(
                AppPale,
                RoundedCornerShape(12.dp)
            )
            .padding(10.dp)
    ) {

        Text(
            text = title,
            fontSize = 10.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = AppGreen
        )

        Text(
            text = "mg/dL",
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}

