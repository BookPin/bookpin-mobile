package com.phase.bookpin.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bookpin.designsystem.generated.resources.Res
import bookpin.designsystem.generated.resources.ic_close
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun BPCloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(36.dp)
            .background(
                color = BookPinTheme.colors.bgSurface,
                shape = CircleShape,
            ),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = BookPinTheme.colors.iconDefault,
        )
    }
}
