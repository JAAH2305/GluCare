
package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.theme.GluCareTheme

private val AddBg = Color(0xFFFAFBF9)
private val AddGreen = Color(0xFF5F7F72)
private val AddText = Color(0xFF30372E)
private val AddGray = Color(0xFF999999)
private val AddBorder = Color(0xFFE8EBE7)
private val AddLightGreen = Color(0xFFEDF2E8)
private val AddOrange = Color(0xFFE9A126)
private val AddLightOrange = Color(0xFFFFF2D8)
private val AddLightBlue = Color(0xFFE9F2F6)

@Composable
fun AddMedicationScreen(onBack: () -> Unit = {}, onSave: () -> Unit = {}) {

    var medicationName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Tablet") }
    var frequency by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("Morning") }
    var customSelected by remember { mutableStateOf(false) }
    var frequencyExpanded by remember { mutableStateOf(false) }
    var timeExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AddBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "‹",
                fontSize = 27.sp,
                color = AddGray,
                modifier = Modifier.clickable(onClick = onBack)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Add Diabetes Medication",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = AddText
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(15.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))

        AddSectionLabel("Medication name")

        AddInput(
            value = medicationName,
            onValueChange = { medicationName = it },
            placeholder = "e.g. Metformin, Insulin, Gliclazide",
            leadingSymbol = "♧"
        )

        Spacer(modifier = Modifier.height(14.dp))

        AddSectionLabel("Medication type")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, AddBorder, RoundedCornerShape(11.dp))
                .background(Color.White, RoundedCornerShape(11.dp))
                .padding(5.dp)
        ) {
            MedicationTypeOption(
                title = "Tablet",
                symbol = "◉",
                selected = selectedType == "Tablet",
                background = AddLightGreen
            ) {
                selectedType = "Tablet"
            }

            MedicationTypeOption(
                title = "Insulin",
                symbol = "♧",
                selected = selectedType == "Insulin",
                background = AddLightBlue
            ) {
                selectedType = "Insulin"
            }

            MedicationTypeOption(
                title = "Other (e.g. GLP-1, SGLT2 inhibitor)",
                symbol = "◇",
                selected = selectedType == "Other",
                background = Color(0xFFF3F0EA)
            ) {
                selectedType = "Other"
            }
        }

        Spacer(modifier = Modifier.height(13.dp))

        AddSectionLabel("Dosage")

        AddInput(
            value = dosage,
            onValueChange = { dosage = it },
            placeholder = "e.g. 500 mg, 10 units, 5 mg",
            leadingSymbol = "▥"
        )

        Spacer(modifier = Modifier.height(14.dp))

        AddSectionLabel("Frequency")

        AddDropdown(
            value = frequency,
            placeholder = "Select frequency",
            expanded = frequencyExpanded,
            options = listOf(
                "Once daily",
                "Twice daily",
                "Three times daily",
                "Four times daily"
            ),
            symbol = "▦",
            onExpandedChange = { frequencyExpanded = it },
            onSelect = {
                frequency = it
                frequencyExpanded = false
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        AddSectionLabel("Choose time(s)")

        AddDropdown(
            value = if (customSelected) "Custom" else selectedTime,
            placeholder = "Select time(s)",
            expanded = timeExpanded,
            options = listOf(
                "Morning",
                "Afternoon",
                "Evening",
                "Custom"
            ),
            symbol = "◷",
            onExpandedChange = { timeExpanded = it },
            onSelect = {
                selectedTime = it
                customSelected = it == "Custom"
                timeExpanded = false
            }
        )

        Spacer(modifier = Modifier.height(9.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            TimeChip(
                text = "☼  Morning",
                selected = selectedTime == "Morning" && !customSelected,
                background = AddLightOrange,
                selectedColor = AddOrange,
                modifier = Modifier.weight(1f)
            ) {
                selectedTime = "Morning"
                customSelected = false
            }

            TimeChip(
                text = "☀  Afternoon",
                selected = selectedTime == "Afternoon" && !customSelected,
                background = AddLightOrange,
                selectedColor = AddOrange,
                modifier = Modifier.weight(1f)
            ) {
                selectedTime = "Afternoon"
                customSelected = false
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            TimeChip(
                text = "◔  Evening",
                selected = selectedTime == "Evening" && !customSelected,
                background = AddLightBlue,
                selectedColor = AddGreen,
                modifier = Modifier.weight(1f)
            ) {
                selectedTime = "Evening"
                customSelected = false
            }

            TimeChip(
                text = "◷  Custom",
                selected = customSelected,
                background = AddLightGreen,
                selectedColor = AddGreen,
                modifier = Modifier.weight(1f)
            ) {
                customSelected = true
                selectedTime = "Custom"
            }
        }

        Spacer(modifier = Modifier.height(17.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(11.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AddGreen
            )
        ) {
            Text(
                text = "Save Medication",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun AddSectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = AddText
    )

    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
private fun AddInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingSymbol: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(43.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .border(1.dp, AddBorder, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = leadingSymbol,
            fontSize = 17.sp,
            color = AddGray
        )

        Spacer(modifier = Modifier.width(9.dp))

        androidx.compose.foundation.text.BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 10.sp,
                color = AddText
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 10.sp,
                            color = AddGray
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun MedicationTypeOption(
    title: String,
    symbol: String,
    selected: Boolean,
    background: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .background(
                if (selected) background else Color.White,
                RoundedCornerShape(7.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = symbol,
            fontSize = 16.sp,
            color = if (selected) AddGreen else AddGray
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = title,
            fontSize = 10.sp,
            color = if (selected) AddText else AddGray,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun AddDropdown(
    value: String,
    placeholder: String,
    expanded: Boolean,
    options: List<String>,
    symbol: String,
    onExpandedChange: (Boolean) -> Unit,
    onSelect: (String) -> Unit
) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(43.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(1.dp, AddBorder, RoundedCornerShape(10.dp))
                .clickable { onExpandedChange(true) }
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = symbol,
                fontSize = 16.sp,
                color = AddGray
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = value.ifEmpty { placeholder },
                fontSize = 10.sp,
                color = if (value.isEmpty()) AddGray else AddText,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "⌄",
                fontSize = 16.sp,
                color = AddGray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            fontSize = 12.sp
                        )
                    },
                    onClick = { onSelect(option) }
                )
            }
        }
    }
}

@Composable
private fun TimeChip(
    text: String,
    selected: Boolean,
    background: Color,
    selectedColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(32.dp)
            .background(
                if (selected) selectedColor else background,
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            color = if (selected) Color.White else AddText,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    widthDp = 393,
    heightDp = 852
)
@Composable
fun AddMedicationScreenPreview() {
    GluCareTheme {
        AddMedicationScreen()
    }
}
