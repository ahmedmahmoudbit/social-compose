package com.example.myapplication.utils.components.colors
import androidx.compose.material3.ColorScheme

// ui/theme/Color.kt
import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.theme.LightColorScheme

val LightSecondaryMain = Color(0xFF4CAF50)
val DarkSecondaryMain = Color(0xFFA5D6A7)

val ColorScheme.isLight: Boolean
    get() = this === LightColorScheme

val ColorScheme.cardColorBackground: Color
    get() = if (this.isLight) Color(0xFFF5F5F5) else Color(0xFF0B0F1E)

val ColorScheme.mainColor: Color
    get() = if (this.isLight) Color(0xFF123CD5) else Color(0xFF123CD5)


val ColorScheme.secondaryMain: Color
    get() = if (this.isLight) LightSecondaryMain else DarkSecondaryMain