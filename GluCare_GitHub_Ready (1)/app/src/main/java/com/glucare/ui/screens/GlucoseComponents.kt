package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glucare.ui.theme.GlucoseColors as Palette

@Composable
internal fun GlucosePage(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBack: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier.fillMaxSize().background(Palette.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        if (title != null) {
            Box(Modifier.fillMaxWidth().heightIn(min = 48.dp), contentAlignment = Alignment.Center) {
                if (onBack != null) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.align(Alignment.CenterStart)
                            .semantics { contentDescription = "Back" },
                    ) { Text("‹", color = Palette.Ink, fontSize = 27.sp) }
                }
                Text(
                    title, Modifier.padding(horizontal = 48.dp), color = Palette.Ink,
                    fontWeight = FontWeight.SemiBold, fontSize = 17.sp, textAlign = TextAlign.Center,
                )
            }
        }
        content()
    }
}

@Composable
internal fun GlucoseCard(
    modifier: Modifier = Modifier,
    color: Color = Palette.Sage,
    onClick: (() -> Unit)? = null,
    outlined: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(15.dp)
    val clickable = if (onClick == null) Modifier else Modifier.clickable(role = Role.Button, onClick = onClick)
    Surface(
        modifier = modifier.then(clickable),
        shape = shape,
        color = color,
        border = if (outlined) androidx.compose.foundation.BorderStroke(1.dp, Palette.Border) else null,
    ) {
        Column(Modifier.padding(14.dp), content = content)
    }
}

@Composable
internal fun SectionLabel(text: String) {
    Text(text, color = Palette.Ink, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
}

@Composable
internal fun SupportingText(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier, color = Palette.SecondaryText, fontSize = 13.sp, lineHeight = 17.sp)
}

@Composable
internal fun GlucoseBadge(status: GlucoseStatus, modifier: Modifier = Modifier) {
    Text(
        status.label,
        modifier.background(status.background, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 5.dp),
        color = Palette.Ink, fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
    )
}

@Composable
internal fun PrimaryGlucoseButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick, modifier = modifier.heightIn(min = 48.dp),
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Palette.Accent, contentColor = Color.White),
    ) { Text(text, fontWeight = FontWeight.SemiBold) }
}
