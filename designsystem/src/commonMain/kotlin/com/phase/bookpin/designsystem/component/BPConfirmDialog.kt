package com.phase.bookpin.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import bookpin.designsystem.generated.resources.Res
import bookpin.designsystem.generated.resources.ic_close
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun BPConfirmDialog(
    title: String,
    description: String,
    cancelText: String,
    confirmText: String,
    confirmButtonColor: Color,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(BookPinTheme.colors.background),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = title,
                    style = BookPinTheme.typography.headlineMedium,
                    color = BookPinTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description,
                    style = BookPinTheme.typography.bodyMedium,
                    color = BookPinTheme.colors.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = cancelText,
                        style = BookPinTheme.typography.titleMedium,
                        color = BookPinTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable(onClick = onDismiss)
                            .background(BookPinTheme.colors.surface)
                            .wrapContentHeight(Alignment.CenterVertically),
                    )

                    Text(
                        text = confirmText,
                        style = BookPinTheme.typography.titleMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable(onClick = onConfirm)
                            .background(confirmButtonColor)
                            .wrapContentHeight(Alignment.CenterVertically),
                    )
                }
            }

            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(36.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = BookPinTheme.colors.onSurfaceVariant,
                )
            }
        }
    }
}
