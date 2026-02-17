package com.phase.bookpin.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.phase.bookpin.designsystem.BookPinTheme

@Composable
fun BPTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val shape = RoundedCornerShape(16.dp)

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = shape,
            )
            .background(
                color = BookPinTheme.colors.bgElevated,
                shape = shape,
            )
            .border(
                width = 1.8.dp,
                color = BookPinTheme.colors.borderSubtle,
                shape = shape,
            ),
        textStyle = BookPinTheme.typography.headlineSmall.copy(
            color = BookPinTheme.colors.textPrimary,
        ),
        cursorBrush = SolidColor(BookPinTheme.colors.buttonPrimary),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = if (leadingIcon != null) 16.dp else 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 16.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = BookPinTheme.typography.headlineSmall,
                            color = BookPinTheme.colors.textPlaceholder,
                        )
                    }
                    innerTextField()
                }
            }
        },
    )
}
