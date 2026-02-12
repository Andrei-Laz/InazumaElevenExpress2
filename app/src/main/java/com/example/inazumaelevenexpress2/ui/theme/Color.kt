package com.example.inazumaelevenexpress2.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.inazumaelevenexpress2.model.enums.Element

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val Orange = Color(0xFFFFAE00)
val DarkOrange = Color(0xFFFF9000)

// ui/theme/Color.kt
@Composable
fun ElementColor(element: Element): Color {
    return when (element) {
        Element.FIRE -> Color(0xFFFF6B6B)
        Element.WIND -> Color(0xFF7ED6DF)
        Element.EARTH -> Color(0xFF96CEB4)
        Element.WOOD -> Color(0xFF55EFc4)
        Element.VOID -> Color(0xFF6C5CE7)
        else -> Color.Gray
    }
}