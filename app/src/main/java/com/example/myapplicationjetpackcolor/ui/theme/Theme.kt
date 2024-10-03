package com.example.myapplicationjetpackcolor.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define your color scheme
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE), // Replace with your primary color
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC6), // Replace with your secondary color
    // Add more colors as needed
)

// Theme function
@Composable
fun MyApplicationColorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography, // Define typography if needed
        content = content
    )
}
