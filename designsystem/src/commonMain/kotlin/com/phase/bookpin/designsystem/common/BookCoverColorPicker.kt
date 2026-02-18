package com.phase.bookpin.designsystem.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.phase.bookpin.designsystem.BookPinTheme
import kotlin.math.absoluteValue

object BookCoverColorPicker {

    @Composable
    fun pick(bookId: Long): Color {
        val colors = palette()
        val index = bookId.hashCode().absoluteValue % colors.size
        return colors[index]
    }

    @Composable
    private fun palette(): List<Color> = listOf(
        BookPinTheme.colors.accentPrimary,
        BookPinTheme.colors.accentSecondary,
    )
}
