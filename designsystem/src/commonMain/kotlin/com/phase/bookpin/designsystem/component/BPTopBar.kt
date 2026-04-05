package com.phase.bookpin.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.phase.bookpin.designsystem.BookPinTheme

@Composable
fun BPTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    showBottomBorder: Boolean = false,
) {
    val borderModifier = if (showBottomBorder) {
        modifier.drawBottomBorder(BookPinTheme.colors.borderSubtle)
    } else {
        modifier
    }

    Box(
        modifier = borderModifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        if (navigationIcon != null || title != null) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (navigationIcon != null) {
                    navigationIcon()
                }
                if (title != null) {
                    Text(
                        text = title,
                        style = BookPinTheme.typography.headlineSmall,
                        color = BookPinTheme.colors.textPrimary,
                    )
                }
            }
        }

        if (actions != null) {
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                actions()
            }
        }
    }
}

private fun Modifier.drawBottomBorder(color: Color): Modifier = this.then(
    Modifier.drawWithContent {
        drawContent()
        drawLine(
            color = color,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 0.6.dp.toPx(),
        )
    },
)
