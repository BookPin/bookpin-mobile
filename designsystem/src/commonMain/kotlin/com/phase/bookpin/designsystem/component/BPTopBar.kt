package com.phase.bookpin.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bookpin.designsystem.generated.resources.Res
import bookpin.designsystem.generated.resources.ic_close
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun BPTopBar(
    title: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawBottomBorder()
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = title,
            style = BookPinTheme.typography.headlineSmall,
            color = BookPinTheme.colors.textPrimary,
            modifier = Modifier.align(Alignment.CenterStart),
        )

        IconButton(
            onClick = onClose,
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = BookPinTheme.colors.bgSurface,
                    shape = CircleShape,
                )
                .align(Alignment.CenterEnd),
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = BookPinTheme.colors.iconDefault,
            )
        }
    }
}

private fun Modifier.drawBottomBorder(): Modifier = this.then(
    Modifier.drawWithContent {
        drawContent()
        drawLine(
            color = Color(0xFFF5EFE6),
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 0.6.dp.toPx(),
        )
    },
)
