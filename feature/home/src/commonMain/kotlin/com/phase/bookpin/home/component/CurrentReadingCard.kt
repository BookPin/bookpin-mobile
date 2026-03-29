package com.phase.bookpin.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import bookpin.home.generated.resources.leave_bookmark
import coil3.compose.AsyncImage
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.model.book.BookItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CurrentlyReadingCard(
    bookItem: BookItem,
    onAddBookmarkClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BookPinTheme.colors.bgElevated,
                shape = RoundedCornerShape(28.dp),
            ).border(
                width = 1.dp,
                color = BookPinTheme.colors.bgSurface,
                shape = RoundedCornerShape(28.dp),
            ).padding(24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .height(144.dp)
                    .shadow(elevation = 4.dp),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(100.dp)
                        .height(144.dp),
                    model = bookItem.imageUrl,
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = bookItem.title,
                    style = BookPinTheme.typography.bodyLarge,
                    color = BookPinTheme.colors.textPrimary,
                    maxLines = 2,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = bookItem.author,
                        style = BookPinTheme.typography.titleSmall,
                        color = BookPinTheme.colors.textSecondary,
                    )

                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .background(
                                color = BookPinTheme.colors.borderDefault,
                                shape = CircleShape,
                            ),
                    )

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

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "${bookItem.currentPage}p / ${bookItem.totalPage}p ",
                        style = BookPinTheme.typography.titleSmall,
                        color = BookPinTheme.colors.textSecondary,
                    )

                    Text(
                        text = "${bookItem.progress}%",
                        style = BookPinTheme.typography.bodyLarge,
                        color = BookPinTheme.colors.textAccent,
                    )
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

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onAddBookmarkClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BookPinTheme.colors.bgMuted,
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 6.dp,
                    ),
                ) {
                    Text(
                        text = stringResource(Res.string.leave_bookmark),
                        style = BookPinTheme.typography.bodyMedium,
                        color = BookPinTheme.colors.textPrimary,
                    )
                }
            }
        }
    }
}
