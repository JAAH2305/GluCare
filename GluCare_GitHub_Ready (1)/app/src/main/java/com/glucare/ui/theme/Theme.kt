package com.glucare.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val GlucoColorScheme = lightColorScheme(
    primary = OlivePrimary,
    onPrimary = AppSurface,
    primaryContainer = OliveLightBg,
    onPrimaryContainer = TextPrimary,
    background = AppBackground,
    onBackground = TextPrimary,
    surface = AppSurface,
    onSurface = TextPrimary,
    surfaceVariant = InputFill,
    onSurfaceVariant = TextSecondary,
    outline = InputBorder
)

@Composable
fun GlucoGuideLogInTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = GlucoColorScheme,
        typography = Typography,
        content = content
    )
}
