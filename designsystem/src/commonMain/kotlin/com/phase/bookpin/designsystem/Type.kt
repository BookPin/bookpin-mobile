package com.phase.bookpin.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import bookpin.designsystem.generated.resources.Res
import bookpin.designsystem.generated.resources.applesdgothicneo_bold
import bookpin.designsystem.generated.resources.applesdgothicneo_extrabold
import bookpin.designsystem.generated.resources.applesdgothicneo_medium
import bookpin.designsystem.generated.resources.applesdgothicneo_regular
import bookpin.designsystem.generated.resources.applesdgothicneo_semibold
import org.jetbrains.compose.resources.Font

@Composable
private fun AppleSDGothicNeo() = FontFamily(
    Font(
        resource = Res.font.applesdgothicneo_regular,
        weight = FontWeight.Normal,
    ),
    Font(
        resource = Res.font.applesdgothicneo_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resource = Res.font.applesdgothicneo_semibold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resource = Res.font.applesdgothicneo_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resource = Res.font.applesdgothicneo_extrabold,
        weight = FontWeight.ExtraBold,
    )
)

@Immutable
data class BookPinTypography(
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,
)

val LocalBookPinTypography = staticCompositionLocalOf {
    BookPinTypography(
        headlineLarge = TextStyle.Default,
        headlineMedium = TextStyle.Default,
        headlineSmall = TextStyle.Default,
        titleLarge = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyMedium = TextStyle.Default,
        bodySmall = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelMedium = TextStyle.Default,
        labelSmall = TextStyle.Default,
    )
}

@Composable
internal fun bookPinTypography(): BookPinTypography {
    val appleFontFamily = AppleSDGothicNeo()
    return remember(appleFontFamily) {
        BookPinTypography(
            headlineLarge = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                lineHeight = 32.sp,
            ),
            headlineMedium = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 28.sp,
            ),
            headlineSmall = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 26.sp,
            ),
            titleLarge = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 26.sp,
            ),
            titleMedium = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            ),
            titleSmall = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
            bodyLarge = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            ),
            bodyMedium = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
            bodySmall = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
            labelLarge = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
            labelMedium = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
            labelSmall = TextStyle(
                fontFamily = appleFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                lineHeight = 16.sp,
            ),
        )
    }
}
