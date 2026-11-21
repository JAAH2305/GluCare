package com.glucare.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.theme.OliveLightBg
import com.glucare.ui.theme.OlivePrimary
import com.glucare.ui.theme.TextPrimary

@Composable
fun GlucoGuideDropIcon(
    modifier: Modifier = Modifier,
    dropColor: Color = OlivePrimary,
    pulseColor: Color = Color.White
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Draw Water Drop Path
        val dropPath = Path().apply {
            moveTo(w * 0.5f, h * 0.08f)
            cubicTo(
                w * 0.72f, h * 0.32f,
                w * 0.92f, h * 0.56f,
                w * 0.92f, h * 0.70f
            )
            cubicTo(
                w * 0.92f, h * 0.92f,
                w * 0.73f, h * 0.98f,
                w * 0.50f, h * 0.98f
            )
            cubicTo(
                w * 0.27f, h * 0.98f,
                w * 0.08f, h * 0.92f,
                w * 0.08f, h * 0.70f
            )
            cubicTo(
                w * 0.08f, h * 0.56f,
                w * 0.28f, h * 0.32f,
                w * 0.50f, h * 0.08f
            )
            close()
        }
        drawPath(path = dropPath, color = dropColor)

        // Draw ECG Pulse Line
        val strokeWidth = w * 0.075f
        val pulsePath = Path().apply {
            val yCenter = h * 0.68f
            moveTo(w * 0.22f, yCenter)
            lineTo(w * 0.38f, yCenter)
            lineTo(w * 0.44f, yCenter + h * 0.09f)
            lineTo(w * 0.52f, yCenter - h * 0.12f)
            lineTo(w * 0.60f, yCenter + h * 0.07f)
            lineTo(w * 0.66f, yCenter)
            lineTo(w * 0.78f, yCenter)
        }

        drawPath(
            path = pulsePath,
            color = pulseColor,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

@Composable
fun GlucoGuideLogoCircle(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    circleBgColor: Color = OliveLightBg,
    dropColor: Color = OlivePrimary
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color = circleBgColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        GlucoGuideDropIcon(
            modifier = Modifier.size(size * 0.52f),
            dropColor = dropColor
        )
    }
}

@Composable
fun GlucoGuideHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color = OliveLightBg, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            GlucoGuideDropIcon(
                modifier = Modifier.size(18.dp),
                dropColor = OlivePrimary
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "GlucoGuide",
            color = TextPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
