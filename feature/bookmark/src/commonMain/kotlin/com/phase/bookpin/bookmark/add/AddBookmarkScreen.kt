package com.phase.bookpin.bookmark.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.bookmark.generated.resources.*
import coil3.compose.AsyncImage
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.designsystem.component.BPTextArea
import com.phase.bookpin.designsystem.component.BPTopBar
import com.phase.bookpin.model.bookmark.BookmarkType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddBookmarkScreen(
    bookId: Long,
    bookmarkType: BookmarkType,
    onNavigateBack: () -> Unit,
) {
    val viewModel: AddBookmarkViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = LocalSnackbarHost.current

    val imagePickerLauncher = rememberImagePickerLauncher(
        onImagePicked = viewModel::onPhotoUriChanged,
    )

    LaunchedEffect(bookmarkType) {
        viewModel.initBookmarkType(bookmarkType)
        when (bookmarkType) {
            BookmarkType.PHOTO_CAMERA -> imagePickerLauncher.launchCamera()
            BookmarkType.PHOTO_GALLERY -> imagePickerLauncher.launchGallery()
            BookmarkType.TEXT -> Unit
        }
    }

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is AddBookmarkSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(it.message)
            }
            AddBookmarkSideEffect.NavigateBack -> onNavigateBack()
            AddBookmarkSideEffect.BookmarkSaved -> onNavigateBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BookPinTheme.colors.bgCanvas),
    ) {
        BPTopBar(
            title = stringResource(Res.string.bookmark_add_title),
            onClose = viewModel::onCloseClick,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp, bottom = 32.dp),
        ) {
            if (state.bookmarkType != BookmarkType.TEXT) {
                PhotoSection(
                    photoUri = state.photoUri,
                    onRetakeClick = {
                        when (state.bookmarkType) {
                            BookmarkType.PHOTO_CAMERA -> imagePickerLauncher.launchCamera()
                            BookmarkType.PHOTO_GALLERY -> imagePickerLauncher.launchGallery()
                            BookmarkType.TEXT -> Unit
                        }
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Text(
                text = if (state.bookmarkType != BookmarkType.TEXT) {
                    stringResource(Res.string.bookmark_extracted_text)
                } else {
                    stringResource(Res.string.bookmark_content_label)
                },
                style = BookPinTheme.typography.titleSmall,
                color = BookPinTheme.colors.textPrimary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            BPTextArea(
                value = state.extractedText,
                onValueChange = viewModel::onExtractedTextChanged,
                placeholder = stringResource(Res.string.bookmark_extracted_text_placeholder),
                minHeight = 172.dp,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(Res.string.bookmark_page_number),
                style = BookPinTheme.typography.titleSmall,
                color = BookPinTheme.colors.textPrimary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            BPTextArea(
                value = state.pageNumber,
                onValueChange = viewModel::onPageNumberChanged,
                placeholder = stringResource(Res.string.bookmark_page_placeholder),
                minHeight = 48.dp,
                modifier = Modifier.fillMaxWidth(0.5f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(Res.string.bookmark_personal_memo),
                style = BookPinTheme.typography.titleSmall,
                color = BookPinTheme.colors.textPrimary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            BPTextArea(
                value = state.personalMemo,
                onValueChange = viewModel::onPersonalMemoChanged,
                placeholder = stringResource(Res.string.bookmark_memo_placeholder),
                minHeight = 100.dp,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = viewModel::onSaveBookmark,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BookPinTheme.colors.buttonPrimary,
                    contentColor = BookPinTheme.colors.textOnAccent,
                ),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_check),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = BookPinTheme.colors.textOnAccent,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(Res.string.bookmark_save),
                    style = BookPinTheme.typography.titleMedium,
                    color = BookPinTheme.colors.textOnAccent,
                )
            }
        }
    }
}

@Composable
private fun PhotoSection(
    photoUri: String?,
    onRetakeClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(BookPinTheme.colors.bgSurface)
            .clickable(onClick = onRetakeClick),
        contentAlignment = Alignment.Center,
    ) {
        if (photoUri != null) {
            AsyncImage(
                model = photoUri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp, max = 400.dp),
                contentScale = ContentScale.Fit,
            )
        } else {
            Icon(
                painter = painterResource(Res.drawable.ic_photo),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = BookPinTheme.colors.iconDefault,
            )
        }
    }
}
