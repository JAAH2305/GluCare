package com.glucare.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable fun GlucoGuideButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
 Button(onClick=onClick, modifier=modifier.fillMaxWidth().height(52.dp), shape=RoundedCornerShape(12.dp)) { Text(text) }
}
