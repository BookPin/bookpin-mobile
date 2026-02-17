package com.phase.bookpin.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object BookPinTheme {
    val colors: BookPinColors
        @Composable
        @ReadOnlyComposable
        get() = LocalBookPinColors.current

    val typography: BookPinTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalBookPinTypography.current
}

@Composable
fun BookPinTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colors = bookPinLightColors
    val typography = bookPinTypography()

    val materialColorScheme = lightColorScheme(
        primary = colors.buttonPrimary,
        onPrimary = colors.bgCanvas,
        primaryContainer = colors.textAccentMuted,
        onPrimaryContainer = colors.bgCanvas,
        secondary = colors.accentPrimary,
        onSecondary = colors.textPrimary,
        secondaryContainer = colors.accentSecondary,
        onSecondaryContainer = colors.textPrimary,
        background = colors.bgCanvas,
        onBackground = colors.textPrimary,
        surface = colors.bgSurface,
        onSurface = colors.textPrimary,
        surfaceVariant = colors.bgMuted,
        onSurfaceVariant = colors.textSecondary,
        outline = colors.borderDefault,
        outlineVariant = colors.textPlaceholder,
    )

    CompositionLocalProvider(
        LocalBookPinColors provides colors,
        LocalBookPinTypography provides typography,
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            content = content,
        )
    }
}
