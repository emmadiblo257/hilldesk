package com.diblo.hilldesk.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HilldeskDarkScheme = darkColorScheme(
    background = Color.Black,
    surface = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    primary = Color(0xFF3B82F6),
    onPrimary = Color.White,
    secondary = Color(0xFF60A5FA),
    onSecondary = Color.White
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = HilldeskDarkScheme,
        content = content
    )
}
