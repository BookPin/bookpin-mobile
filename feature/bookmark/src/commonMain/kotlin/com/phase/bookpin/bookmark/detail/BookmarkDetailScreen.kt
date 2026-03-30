package com.phase.bookpin.bookmark.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import bookpin.bookmark.generated.resources.*
import coil3.compose.AsyncImage
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.designsystem.component.BPTopBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BookmarkDetailScreen(
    bookTitle: String,
    bookAuthor: String,
    bookImageUrl: String,
    pageNumber: Int,
    extractedText: String,
    note: String,
    imageUrl: String,
    createdAt: String,
    onNavigateBack: () -> Unit,
) {
    val snackbarHost = LocalSnackbarHost.current
    val deletePreparingMessage = stringResource(Res.string.bookmark_detail_delete_preparing)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BookPinTheme.colors.bgCanvas),
    ) {
        BPTopBar(
            navigationIcon = {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.size(36.dp),
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_back),
                        contentDescription = stringResource(Res.string.cd_back),
                        modifier = Modifier.size(20.dp),
                        tint = BookPinTheme.colors.iconDefault,
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { snackbarHost.showSnackbar(deletePreparingMessage) },
                    modifier = Modifier.size(36.dp),
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = stringResource(Res.string.cd_delete_bookmark),
                        modifier = Modifier.size(20.dp),
                        tint = BookPinTheme.colors.iconDefault,
                    )
                }
            },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            BookInfoSection(
                bookTitle = bookTitle,
                bookAuthor = bookAuthor,
                bookImageUrl = bookImageUrl,
                pageNumber = pageNumber,
                createdAt = createdAt,
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (imageUrl.isNotEmpty()) {
                BookmarkPhotoSection(imageUrl = imageUrl)
                Spacer(modifier = Modifier.height(24.dp))
            }

            BookContentSection(extractedText = extractedText)

            Spacer(modifier = Modifier.height(24.dp))

            PersonalMemoSection(note = note)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun BookInfoSection(
    bookTitle: String,
    bookAuthor: String,
    bookImageUrl: String,
    pageNumber: Int,
    createdAt: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = bookImageUrl,
            contentDescription = null,
            modifier = Modifier
                .width(48.dp)
                .height(72.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(BookPinTheme.colors.bgSurface),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = bookTitle,
                style = BookPinTheme.typography.titleMedium,
                color = BookPinTheme.colors.textPrimary,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = bookAuthor,
                style = BookPinTheme.typography.bodyMedium,
                color = BookPinTheme.colors.textSecondary,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "p.$pageNumber · $createdAt",
                style = BookPinTheme.typography.bodySmall,
                color = BookPinTheme.colors.textSecondary,
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    HorizontalDivider(
        color = BookPinTheme.colors.borderSubtle.copy(alpha = 0.5f),
        thickness = 1.dp,
    )
}

@Composable
private fun BookmarkPhotoSection(
    imageUrl: String,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = BookPinTheme.colors.borderSubtle,
                shape = RoundedCornerShape(16.dp),
            ),
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
private fun BookContentSection(
    extractedText: String,
) {
    Text(
        text = stringResource(Res.string.bookmark_detail_content),
        style = BookPinTheme.typography.titleMedium,
        color = BookPinTheme.colors.textTertiary,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
            ).background(
                color = BookPinTheme.colors.bgElevated,
                shape = RoundedCornerShape(16.dp),
            ).padding(16.dp),
    ) {
        Text(
            text = extractedText,
            style = BookPinTheme.typography.bodyMedium,
            color = BookPinTheme.colors.textPrimary,
        )
    }
}

@Composable
private fun PersonalMemoSection(
    note: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawDashedTopBorder(
                color = BookPinTheme.colors.borderDefault,
            ).padding(top = 16.dp),
    ) {
        Column {
            Text(
                text = stringResource(Res.string.bookmark_detail_memo),
                style = BookPinTheme.typography.titleMedium,
                color = BookPinTheme.colors.textTertiary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(16.dp),
                    ).background(
                        color = BookPinTheme.colors.bgElevated,
                        shape = RoundedCornerShape(16.dp),
                    ).padding(16.dp),
            ) {
                Text(
                    text = note.ifEmpty { "-" },
                    style = BookPinTheme.typography.bodyMedium.copy(
                        fontStyle = if (note.isEmpty()) FontStyle.Italic else FontStyle.Normal,
                    ),
                    color = if (note.isEmpty()) {
                        BookPinTheme.colors.textPlaceholder
                    } else {
                        BookPinTheme.colors.textPrimary
                    },
                )
            }
        }
    }
}

private fun Modifier.drawDashedTopBorder(
    color: Color,
): Modifier = this.then(
    Modifier.drawBehind {
        val dashWidth = 6.dp.toPx()
        val dashGap = 4.dp.toPx()
        val strokeWidth = 1.dp.toPx()
        var x = 0f
        while (x < size.width) {
            drawLine(
                color = color,
                start = Offset(x, 0f),
                end = Offset(
                    (x + dashWidth).coerceAtMost(size.width),
                    0f,
                ),
                strokeWidth = strokeWidth,
            )
            x += dashWidth + dashGap
        }
    },
)
