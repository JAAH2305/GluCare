package com.glucare.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ScreenBg = Color(0xFFFAFBF9)
private val MainGreen = Color(0xFF5F7F72)
private val TextDark = Color(0xFF30372E)
private val TextGray = Color(0xFF999999)
private val CardBorder = Color(0xFFE7EAE6)

@Composable
fun MyMedicationsScreen(onAdd: () -> Unit = {}, onDetails: () -> Unit = {}, onSchedule: () -> Unit = {}, onHome: () -> Unit = {}, onGlucose: () -> Unit = {}, onMore: () -> Unit = {}) {
    var search by remember { mutableStateOf("") }

    val medications = listOf(
        MedicationData(
            "Metformin",
            "500 mg • Tablet",
            "Twice daily • With meals",
            "pill",
            Color(0xFFE8F0EA)
        ),
        MedicationData(
            "Insulin",
            "10 units • Injection",
            "Once daily • Evening",
            "syringe",
            Color(0xFFE9F2F6)
        ),
        MedicationData(
            "Gliclazide",
            "80 mg • Tablet",
            "Once daily • Morning",
            "pill",
            Color(0xFFF3F0EA)
        )
    )

    Scaffold(
        containerColor = ScreenBg
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "My Medications",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Manage your diabetes medications",
                    fontSize = 12.sp,
                    color = TextGray
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(12.dp)
                        )
                        .border(
                            1.dp,
                            CardBorder,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchIcon()

                    Spacer(modifier = Modifier.width(10.dp))

                    BasicTextField(
                        value = search,
                        onValueChange = { search = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 12.sp,
                            color = TextDark
                        ),
                        decorationBox = { innerTextField ->
                            Box {
                                if (search.isEmpty()) {
                                    Text(
                                        text = "Search medications",
                                        fontSize = 12.sp,
                                        color = TextGray
                                    )
                                }
                                innerTextField()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                medications.filter {
                    it.name.contains(search, ignoreCase = true)
                }.forEach { medication ->
                    MedicationItemCard(medication, onDetails)
                    Spacer(modifier = Modifier.height(9.dp))
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CalendarIcon()

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Medication schedule",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "Stay on track with your daily doses",
                            fontSize = 11.sp,
                            color = TextGray
                        )
                    }

                    Text(
                        text = "›",
                        fontSize = 23.sp,
                        color = TextGray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GluCare",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.width(90.dp))

                    NotificationIcon()
                }
            }

            FloatingActionButton(
                onClick = onAdd,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = 20.dp,
                        bottom = 83.dp
                    )
                    .size(58.dp),
                containerColor = MainGreen,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Text(
                    text = "+",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

private data class MedicationData(
    val name: String,
    val dosage: String,
    val schedule: String,
    val type: String,
    val background: Color
)

@Composable
private fun MedicationItemCard(
    medication: MedicationData, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(69.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            CardBorder
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        medication.background,
                        RoundedCornerShape(11.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                MedicationSymbol(type = medication.type)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = medication.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = medication.dosage,
                    fontSize = 10.sp,
                    color = TextGray
                )

                Text(
                    text = medication.schedule,
                    fontSize = 10.sp,
                    color = MainGreen
                )
            }

            Text(
                text = "›",
                fontSize = 22.sp,
                color = Color(0xFFB7BDB8)
            )
        }
    }
}

@Composable
private fun MedicationSymbol(type: String) {
    Canvas(modifier = Modifier.size(23.dp)) {
        val color = MainGreen

        if (type == "pill") {
            drawRoundRect(
                color = color,
                topLeft = Offset(
                    size.width * 0.2f,
                    size.height * 0.35f
                ),
                size = Size(
                    size.width * 0.6f,
                    size.height * 0.3f
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    size.height * 0.2f
                ),
                style = Stroke(width = 1.5.dp.toPx())
            )

            drawLine(
                color,
                Offset(size.width * 0.5f, size.height * 0.35f),
                Offset(size.width * 0.5f, size.height * 0.65f),
                strokeWidth = 1.2.dp.toPx()
            )
        } else {
            drawLine(
                color,
                Offset(size.width * 0.2f, size.height * 0.8f),
                Offset(size.width * 0.8f, size.height * 0.2f),
                strokeWidth = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )

            drawLine(
                color,
                Offset(size.width * 0.65f, size.height * 0.15f),
                Offset(size.width * 0.85f, size.height * 0.35f),
                strokeWidth = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )

            drawLine(
                color,
                Offset(size.width * 0.15f, size.height * 0.65f),
                Offset(size.width * 0.35f, size.height * 0.85f),
                strokeWidth = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun SearchIcon() {
    Canvas(modifier = Modifier.size(17.dp)) {
        drawCircle(
            color = TextGray,
            radius = size.width * 0.3f,
            center = Offset(
                size.width * 0.4f,
                size.height * 0.4f
            ),
            style = Stroke(width = 1.4.dp.toPx())
        )

        drawLine(
            TextGray,
            Offset(size.width * 0.62f, size.height * 0.62f),
            Offset(size.width * 0.9f, size.height * 0.9f),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun CalendarIcon() {
    Canvas(modifier = Modifier.size(20.dp)) {
        drawRoundRect(
            MainGreen,
            topLeft = Offset(
                size.width * 0.15f,
                size.height * 0.2f
            ),
            size = Size(
                size.width * 0.7f,
                size.height * 0.65f
            ),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                2.dp.toPx()
            ),
            style = Stroke(width = 1.2.dp.toPx())
        )

        drawLine(
            MainGreen,
            Offset(size.width * 0.15f, size.height * 0.4f),
            Offset(size.width * 0.85f, size.height * 0.4f),
            strokeWidth = 1.2.dp.toPx()
        )

        drawLine(
            MainGreen,
            Offset(size.width * 0.35f, size.height * 0.08f),
            Offset(size.width * 0.35f, size.height * 0.3f),
            strokeWidth = 1.3.dp.toPx()
        )

        drawLine(
            MainGreen,
            Offset(size.width * 0.65f, size.height * 0.08f),
            Offset(size.width * 0.65f, size.height * 0.3f),
            strokeWidth = 1.3.dp.toPx()
        )
    }
}

@Composable
private fun NotificationIcon() {
    Canvas(modifier = Modifier.size(19.dp)) {
        drawArc(
            color = TextGray,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(
                size.width * 0.2f,
                size.height * 0.1f
            ),
            size = Size(
                size.width * 0.6f,
                size.height * 0.7f
            ),
            style = Stroke(width = 1.3.dp.toPx())
        )

        drawLine(
            TextGray,
            Offset(size.width * 0.2f, size.height * 0.75f),
            Offset(size.width * 0.8f, size.height * 0.75f),
            strokeWidth = 1.3.dp.toPx()
        )

        drawCircle(
            TextGray,
            radius = size.width * 0.07f,
            center = Offset(size.width * 0.5f, size.height * 0.9f)
        )
    }
}

@Composable
private fun MedicationBottomBar(onHome: () -> Unit, onGlucose: () -> Unit, onMore: () -> Unit) {
    Surface(
        color = ScreenBg,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .border(0.5.dp, CardBorder)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationItem("⌂", "Home", false, onHome)
            NavigationItem("◯", "Glucose", false, onGlucose)
            NavigationItem("♧", "Medications", true, {})
            NavigationItem("•••", "More", false, onMore)
        }
    }
}

@Composable
private fun NavigationItem(
    symbol: String,
    label: String,
    selected: Boolean, onClick: () -> Unit
) {
    val color = if (selected) MainGreen else TextGray

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = symbol,
            fontSize = 18.sp,
            color = color
        )

        Text(
            text = label,
            fontSize = 9.sp,
            color = color,
            fontWeight = if (selected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=393dp,height=852dp"
)
@Composable
fun MyMedicationsScreenPreview() {
    MyMedicationsScreen()
}