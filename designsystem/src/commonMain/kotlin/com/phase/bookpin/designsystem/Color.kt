package com.phase.bookpin.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val Cream = Color(0xFFFFF8F0)
private val LightBeige = Color(0xFFF5EFE6)
private val Tan = Color(0xFFE8DCC4)
private val Gold = Color(0xFFC9A87C)
private val MutedGold = Color(0xFFB8956B)
private val Brown = Color(0xFF6B5744)
private val LightBrown = Color(0xFFA0826D)
private val Placeholder = Color(0xFFD4B896)
private val Teal = Color(0xFF9DBEB7)
private val Peach = Color(0xFFE8B4A0)

@Immutable
data class BookPinColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val outlineVariant: Color,
)

internal val bookPinLightColors = BookPinColors(
    primary = Gold,
    onPrimary = Cream,
    primaryContainer = MutedGold,
    onPrimaryContainer = Cream,
    secondary = Teal,
    onSecondary = Brown,
    secondaryContainer = Peach,
    onSecondaryContainer = Brown,
    background = Cream,
    onBackground = Brown,
    surface = LightBeige,
    onSurface = Brown,
    surfaceVariant = Tan,
    onSurfaceVariant = LightBrown,
    outline = Tan,
    outlineVariant = Placeholder,
)

val LocalBookPinColors = staticCompositionLocalOf { bookPinLightColors }
