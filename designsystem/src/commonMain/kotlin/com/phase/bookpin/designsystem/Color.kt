package com.phase.bookpin.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val White = Color(0xFFFFFFFF)

private val Cream100 = Color(0xFFFFF8F0)
private val Cream200 = Color(0xFFF5EFE6)
private val Cream300 = Color(0xFFE8DCC4)

private val Gold100 = Color(0xFFC9A87C)
private val Gold200 = Color(0xFFB8956B)

private val Brown100 = Color(0xFFD4B896)
private val Brown200 = Color(0xFFA0826D)
private val Brown300 = Color(0xFF8B7355)
private val Brown400 = Color(0xFF6B5744)

private val Peach100 = Color(0xFFE8B4A0)
private val Terracotta100 = Color(0xFFD08C7E)
private val Teal100 = Color(0xFF9DBEB7)

@Immutable
data class BookPinColors(
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textAccent: Color,
    val textAccentMuted: Color,
    val textPlaceholder: Color,
    val textOnAccent: Color,
    val iconDefault: Color,
    val iconOnAccent: Color,
    val bgCanvas: Color,
    val bgSurface: Color,
    val bgElevated: Color,
    val bgMuted: Color,
    val borderDefault: Color,
    val borderSubtle: Color,
    val buttonPrimary: Color,
    val accentPrimary: Color,
    val accentSecondary: Color,
    val error: Color,
    val shadow: Color,
)

internal val bookPinLightColors = BookPinColors(
    textPrimary = Brown400,
    textSecondary = Brown200,
    textTertiary = Brown300,
    textAccent = Gold100,
    textAccentMuted = Gold200,
    textPlaceholder = Brown100,
    textOnAccent = White,
    iconDefault = Brown200,
    iconOnAccent = White,
    bgCanvas = Cream100,
    bgSurface = Cream200,
    bgElevated = White,
    bgMuted = Cream300,
    borderDefault = Cream300,
    borderSubtle = Cream200,
    buttonPrimary = Gold100,
    accentPrimary = Teal100,
    accentSecondary = Peach100,
    error = Terracotta100,
    shadow = Brown400,
)

val LocalBookPinColors = staticCompositionLocalOf { bookPinLightColors }
