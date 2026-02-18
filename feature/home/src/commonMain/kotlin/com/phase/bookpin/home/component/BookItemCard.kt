package com.phase.bookpin.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bookpin.home.generated.resources.Res
import bookpin.home.generated.resources.bookmark
import bookpin.home.generated.resources.cd_bookmark
import coil3.compose.AsyncImage
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.model.book.BookItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BookItemCard(
    bookItem: BookItem,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BookPinTheme.colors.bgElevated,
                shape = RoundedCornerShape(16.dp),
            ).clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                modifier = Modifier.shadow(elevation = 4.dp),
                model = bookItem.imageUrl,
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = bookItem.title,
            style = BookPinTheme.typography.titleSmall,
            color = BookPinTheme.colors.textPrimary,
            maxLines = 1,
        )

        Text(
            text = bookItem.author,
            style = BookPinTheme.typography.bodySmall,
            color = BookPinTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${bookItem.currentPage} / ${bookItem.totalPage} ",
                    style = BookPinTheme.typography.labelSmall,
                    color = BookPinTheme.colors.textSecondary,
                )

                Text(
                    text = "(${bookItem.progress}%)",
                    style = BookPinTheme.typography.labelMedium,
                    color = BookPinTheme.colors.textAccent,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(start = 4.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.bookmark),
                    contentDescription = stringResource(Res.string.cd_bookmark),
                    modifier = Modifier.size(14.dp),
                    tint = Color.Unspecified,
                )

                Text(
                    text = "${bookItem.bookmarkCount}",
                    style = BookPinTheme.typography.labelMedium,
                    color = BookPinTheme.colors.textPrimary.copy(alpha = 0.8f),
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(
                    color = BookPinTheme.colors.bgSurface,
                    shape = CircleShape,
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth((bookItem.progress / 100f).toFloat())
                    .background(
                        color = BookPinTheme.colors.buttonPrimary,
                        shape = CircleShape,
                    ),
            )
        }
    }
}
