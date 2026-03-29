package com.phase.bookpin.bookmark.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bookpin.bookmark.generated.resources.*
import com.phase.bookpin.bookmark.component.BookmarkTypeOptionCard
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.designsystem.component.BPTopBar
import com.phase.bookpin.model.bookmark.BookmarkType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BookmarkTypeSelectScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddBookmark: (BookmarkType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BookPinTheme.colors.bgCanvas),
    ) {
        BPTopBar(
            title = stringResource(Res.string.bookmark_add_title),
            onClose = onNavigateBack,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 32.dp),
        ) {
            Text(
                text = stringResource(Res.string.bookmark_type_question),
                style = BookPinTheme.typography.bodyLarge,
                color = BookPinTheme.colors.textSecondary,
            )

            Spacer(modifier = Modifier.height(24.dp))

            BookmarkTypeOptionCard(
                icon = painterResource(Res.drawable.ic_camera),
                title = stringResource(Res.string.bookmark_type_camera),
                subtitle = stringResource(Res.string.bookmark_type_camera_desc),
                onClick = { onNavigateToAddBookmark(BookmarkType.PHOTO_CAMERA) },
            )

            Spacer(modifier = Modifier.height(12.dp))

            BookmarkTypeOptionCard(
                icon = painterResource(Res.drawable.ic_gallery),
                title = stringResource(Res.string.bookmark_type_gallery),
                subtitle = stringResource(Res.string.bookmark_type_gallery_desc),
                onClick = { onNavigateToAddBookmark(BookmarkType.PHOTO_GALLERY) },
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onNavigateToAddBookmark(BookmarkType.TEXT) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BookPinTheme.colors.buttonPrimary,
                    contentColor = BookPinTheme.colors.textOnAccent,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.bookmark_type_text),
                    style = BookPinTheme.typography.titleMedium,
                    color = BookPinTheme.colors.textOnAccent,
                )
            }
        }
    }
}
