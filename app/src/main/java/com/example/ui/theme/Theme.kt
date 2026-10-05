package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CyberColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color(0xFF001B24),
    primaryContainer = Color(0xFF003648),
    onPrimaryContainer = Color(0xFFA6EEFF),
    secondary = NeonPurple,
    onSecondary = Color(0xFF280036),
    secondaryContainer = Color(0xFF45005E),
    onSecondaryContainer = Color(0xFFF6D8FF),
    tertiary = NeonEmerald,
    onTertiary = Color(0xFF002911),
    tertiaryContainer = Color(0xFF005327),
    onTertiaryContainer = Color(0xFF8CF8AC),
    background = CyberDarkBg,
    onBackground = TextPrimary,
    surface = CyberSurface,
    onSurface = TextPrimary,
    surfaceVariant = CyberCard,
    onSurfaceVariant = TextSecondary,
    outline = CyberBorder,
    outlineVariant = CyberBorderGlow,
    error = NeonRose,
    onError = Color.White
)

@Composable
fun AX01AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CyberColorScheme,
        typography = Typography,
        content = content
    )
}

// Backward compatibility alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    AX01AppTheme(content = content)
}
