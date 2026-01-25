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
    content: @Composable () -> Unit,
) {
    val colors = bookPinLightColors
    val typography = bookPinTypography()

    val materialColorScheme = lightColorScheme(
        primary = colors.primary,
        onPrimary = colors.onPrimary,
        primaryContainer = colors.primaryContainer,
        onPrimaryContainer = colors.onPrimaryContainer,
        secondary = colors.secondary,
        onSecondary = colors.onSecondary,
        secondaryContainer = colors.secondaryContainer,
        onSecondaryContainer = colors.onSecondaryContainer,
        background = colors.background,
        onBackground = colors.onBackground,
        surface = colors.surface,
        onSurface = colors.onSurface,
        surfaceVariant = colors.surfaceVariant,
        onSurfaceVariant = colors.onSurfaceVariant,
        outline = colors.outline,
        outlineVariant = colors.outlineVariant,
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
