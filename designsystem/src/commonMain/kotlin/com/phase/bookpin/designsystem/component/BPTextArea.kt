package com.phase.bookpin.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.phase.bookpin.designsystem.BookPinTheme

@Composable
fun BPTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    minHeight: Dp = 100.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val shape = RoundedCornerShape(16.dp)

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BookPinTheme.colors.bgElevated,
                shape = shape,
            )
            .border(
                width = 1.dp,
                color = BookPinTheme.colors.borderDefault,
                shape = shape,
            ),
        textStyle = BookPinTheme.typography.bodyMedium.copy(
            color = BookPinTheme.colors.textPrimary,
        ),
        cursorBrush = SolidColor(BookPinTheme.colors.buttonPrimary),
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = minHeight)
                    .padding(16.dp),
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = BookPinTheme.typography.bodyMedium,
                        color = BookPinTheme.colors.textPlaceholder,
                    )
                }
                innerTextField()
            }
        },
    )
}
