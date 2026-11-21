package com.glucare.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DetailsBg = Color(0xFFFAFBF9)
private val DetailsGreen = Color(0xFF5F7F72)
private val DetailsText = Color(0xFF30372E)
private val DetailsGray = Color(0xFF999999)
private val DetailsBorder = Color(0xFFE8EBE7)

@Composable
fun MedicationDetailsScreen(onBack: () -> Unit = {}, onEdit: () -> Unit = {}, onDelete: () -> Unit = {}) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DetailsBg)
            .padding(horizontal = 17.dp)
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "‹",
                modifier = Modifier.clickable(onClick = onBack),
                fontSize = 27.sp,
                color = DetailsGray
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Medication Details",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = DetailsText
            )

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.width(15.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(108.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        Color(0xFFEDF3EE),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                CapsuleDrawing(
                    modifier = Modifier.size(65.dp)
                )
            }

            PlantDrawing(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(48.dp)
                    .height(105.dp)
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = "Metformin",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DetailsText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = "500 mg • Tablet",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 10.sp,
            color = DetailsGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(
                    Color(0xFFEDF3EE),
                    RoundedCornerShape(20.dp)
                )
                .padding(
                    horizontal = 11.dp,
                    vertical = 4.dp
                )
        ) {
            Text(
                text = "For blood glucose control",
                fontSize = 9.sp,
                color = DetailsGreen
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Helps lower blood sugar by improving how your body uses insulin.",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 10.sp,
            color = DetailsText,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(13.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                DetailsBorder
            ),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 10.dp,
                    vertical = 7.dp
                )
            ) {
                DetailInformation(
                    symbol = "♧",
                    title = "Dose",
                    value = "500 mg",
                    arrow = true
                )

                DetailInformation(
                    symbol = "◷",
                    title = "Schedule",
                    value = "Custom schedule",
                    arrow = true
                )

                DetailInformation(
                    symbol = "▦",
                    title = "Frequency",
                    value = "Twice daily",
                    arrow = true
                )

                DetailInformation(
                    symbol = "▤",
                    title = "Additional notes",
                    value = "Take as directed by your healthcare provider."
                )

                DetailInformation(
                    symbol = "♧",
                    title = "Glucose monitoring",
                    value = "Check your blood glucose regularly if needed."
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Adherence",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = DetailsText
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "80%",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = DetailsText
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        LinearProgressIndicator(
            progress = { 0.8f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            color = Color(0xFFB8C9B9),
            trackColor = Color(0xFFE8EEE8)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "40 of 50 doses taken this month",
            fontSize = 8.sp,
            color = DetailsGray
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            OutlinedButton(
                onClick = onEdit,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = RoundedCornerShape(11.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color(0xFFD6E0D8)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = DetailsGreen
                )
            ) {
                Text(
                    text = "✎  Edit",
                    fontSize = 11.sp
                )
            }

            OutlinedButton(
                onClick = onDelete,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = RoundedCornerShape(11.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color(0xFFEEDDDD)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFC77D7D)
                )
            ) {
                Text(
                    text = "▢  Delete",
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun CapsuleDrawing(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        rotate(35f) {
            val capsuleWidth = size.width * 0.58f
            val capsuleHeight = size.height * 0.96f

            val left = (size.width - capsuleWidth) / 2
            val top = (size.height - capsuleHeight) / 2

            val radius = CornerRadius(capsuleWidth / 2)

            drawRoundRect(
                color = Color.White,
                topLeft = Offset(left, top),
                size = Size(capsuleWidth, capsuleHeight),
                cornerRadius = radius
            )

            drawRoundRect(
                color = DetailsGreen,
                topLeft = Offset(left, top),
                size = Size(
                    capsuleWidth,
                    capsuleHeight / 2
                ),
                cornerRadius = radius
            )

            drawRect(
                color = Color.White,
                topLeft = Offset(
                    left,
                    top + capsuleHeight / 2
                ),
                size = Size(
                    capsuleWidth,
                    capsuleHeight / 2
                )
            )

            drawLine(
                color = DetailsGreen,
                start = Offset(
                    left,
                    top + capsuleHeight / 2
                ),
                end = Offset(
                    left + capsuleWidth,
                    top + capsuleHeight / 2
                ),
                strokeWidth = 1.5.dp.toPx()
            )

            drawRoundRect(
                color = DetailsGreen,
                topLeft = Offset(left, top),
                size = Size(capsuleWidth, capsuleHeight),
                cornerRadius = radius,
                style = Stroke(width = 1.5.dp.toPx())
            )
        }
    }
}

@Composable
private fun PlantDrawing(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {

        val stem = Path().apply {
            moveTo(size.width * 0.8f, size.height * 0.95f)

            cubicTo(
                size.width * 0.35f,
                size.height * 0.7f,
                size.width * 0.8f,
                size.height * 0.3f,
                size.width * 0.45f,
                size.height * 0.05f
            )
        }

        drawPath(
            path = stem,
            color = Color(0xFFB8C9B9),
            style = Stroke(width = 1.7.dp.toPx())
        )

        val leaves = listOf(
            Triple(0.65f, 0.17f, -35f),
            Triple(0.28f, 0.34f, 35f),
            Triple(0.72f, 0.51f, -35f),
            Triple(0.32f, 0.68f, 35f),
            Triple(0.78f, 0.84f, -35f)
        )

        leaves.forEach { (x, y, angle) ->
            rotate(
                degrees = angle,
                pivot = Offset(
                    size.width * x,
                    size.height * y
                )
            ) {
                drawOval(
                    color = Color(0xFFD1DDD0),
                    topLeft = Offset(
                        size.width * x - 6.dp.toPx(),
                        size.height * y - 12.dp.toPx()
                    ),
                    size = Size(
                        12.dp.toPx(),
                        24.dp.toPx()
                    )
                )
            }
        }
    }
}

@Composable
private fun DetailInformation(
    symbol: String,
    title: String,
    value: String,
    arrow: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = symbol,
            fontSize = 16.sp,
            color = DetailsGreen,
            modifier = Modifier.width(28.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 9.sp,
                color = DetailsGray
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value,
                fontSize = 10.sp,
                color = DetailsText,
                lineHeight = 13.sp
            )
        }

        if (arrow) {
            Text(
                text = "›",
                fontSize = 19.sp,
                color = DetailsGray
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=393dp,height=852dp"
)
@Composable
fun MedicationDetailsScreenPreview() {
    MedicationDetailsScreen()
}