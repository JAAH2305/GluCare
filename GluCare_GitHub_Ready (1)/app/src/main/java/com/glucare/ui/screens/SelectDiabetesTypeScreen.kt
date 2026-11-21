package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.components.CheckmarkBadge
import com.glucare.ui.components.GlucoGuideButton
import com.glucare.ui.components.GlucoIcons
import com.glucare.ui.components.ProfileProgressHeader
import com.glucare.ui.components.SecurityBadge
import com.glucare.ui.theme.AppBackground
import com.glucare.ui.theme.AppSurface
import com.glucare.ui.theme.GlucoGuideLogInTheme
import com.glucare.ui.theme.InputBorder
import com.glucare.ui.theme.OliveBorder
import com.glucare.ui.theme.OliveLightBg
import com.glucare.ui.theme.OliveLightCard
import com.glucare.ui.theme.OlivePrimary
import com.glucare.ui.theme.TextPrimary
import com.glucare.ui.theme.TextSecondary

enum class DiabetesType {
    TYPE_1,
    TYPE_2,
    GESTATIONAL
}

@Composable
fun SelectDiabetesTypeScreen(
    onBackClick: () -> Unit = {},
    onGetStartedClick: () -> Unit = {}
) {
    var selectedType by remember { mutableStateOf(DiabetesType.TYPE_2) }

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
                currentStep = 2,
                totalSteps = 2,
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Care that fits you",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Select your diabetes type so we can tailor your tracking experience.",
                color = TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Option 1: Type 1
            DiabetesTypeCard(
                title = "Type 1",
                subtitle = "Support for daily insulin care.",
                icon = GlucoIcons.WaterDrop,
                isSelected = selectedType == DiabetesType.TYPE_1,
                onClick = { selectedType = DiabetesType.TYPE_1 }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Option 2: Type 2
            DiabetesTypeCard(
                title = "Type 2",
                subtitle = "Build healthy, everyday habits.",
                icon = GlucoIcons.Leaf,
                isSelected = selectedType == DiabetesType.TYPE_2,
                onClick = { selectedType = DiabetesType.TYPE_2 }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Option 3: Gestational
            DiabetesTypeCard(
                title = "Gestational",
                subtitle = "Care during your pregnancy.",
                icon = GlucoIcons.Heart,
                isSelected = selectedType == DiabetesType.GESTATIONAL,
                onClick = { selectedType = DiabetesType.GESTATIONAL }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Not sure? Choose the type your care team has shared with you. You can change it later.",
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Get Started Button
            GlucoGuideButton(
                text = "Get Started",
                onClick = onGetStartedClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecurityBadge()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun DiabetesTypeCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardBg = if (isSelected) OliveLightCard else AppSurface
    val borderCol = if (isSelected) OliveBorder else InputBorder

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = cardBg, shape = RoundedCornerShape(16.dp))
            .border(width = 1.5.dp, color = borderCol, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(color = OliveLightBg, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OlivePrimary,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 13.sp
            )
        }

        if (isSelected) {
            CheckmarkBadge(
                modifier = Modifier.size(24.dp),
                color = OlivePrimary
            )
        } else {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .border(width = 1.5.dp, color = InputBorder, shape = CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectDiabetesTypeScreenPreview() {
    GlucoGuideLogInTheme {
        SelectDiabetesTypeScreen()
    }
}
