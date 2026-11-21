package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.components.GlucoGuideButton
import com.glucare.ui.components.GlucoGuideTextField
import com.glucare.ui.components.InfoCard
import com.glucare.ui.components.ProfileProgressHeader
import com.glucare.ui.components.SecurityBadge
import com.glucare.ui.theme.AppBackground
import com.glucare.ui.theme.GlucoGuideLogInTheme
import com.glucare.ui.theme.TextPrimary
import com.glucare.ui.theme.TextSecondary

@Composable
fun CreateProfileScreen(
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ProfileProgressHeader(
                currentStep = 1,
                totalSteps = 2,
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Create your profile",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "A few details help us make GlucoGuide feel more like you.",
                color = TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Age Field
            GlucoGuideTextField(
                value = age,
                onValueChange = { age = it },
                label = "Age",
                placeholder = "Enter your age",
                trailingText = "years",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Height Field
            GlucoGuideTextField(
                value = height,
                onValueChange = { height = it },
                label = "Height",
                placeholder = "Enter your height",
                trailingText = "cm",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Weight Field
            GlucoGuideTextField(
                value = weight,
                onValueChange = { weight = it },
                label = "Weight",
                placeholder = "Enter your weight",
                trailingText = "kg",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Info Card
            InfoCard(
                text = "Made for your everyday life. You can update these details anytime."
            )

            Spacer(modifier = Modifier.weight(1f))

            // Next Button
            GlucoGuideButton(
                text = "Next",
                onClick = onNextClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecurityBadge()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateProfileScreenPreview() {
    GlucoGuideLogInTheme {
        CreateProfileScreen()
    }
}
