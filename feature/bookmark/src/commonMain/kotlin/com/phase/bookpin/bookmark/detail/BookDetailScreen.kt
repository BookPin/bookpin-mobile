package com.phase.bookpin.bookmark.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.bookmark.generated.resources.*
import coil3.compose.AsyncImage
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookDetailScreen(
    bookId: Long,
    onNavigateBack: () -> Unit = {},
    onNavigateToAddBookmark: () -> Unit = {},
) {
    val viewModel: BookDetailViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = LocalSnackbarHost.current

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is BookDetailSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(it.message)
            }
            BookDetailSideEffect.NavigateBack -> onNavigateBack()
            BookDetailSideEffect.NavigateToAddBookmark -> onNavigateToAddBookmark()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::onAddBookmarkClick,
                containerColor = BookPinTheme.colors.buttonPrimary,
                contentColor = BookPinTheme.colors.iconOnAccent,
                shape = CircleShape,
                modifier = Modifier.size(56.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_add),
                    contentDescription = stringResource(Res.string.cd_add_bookmark),
                    modifier = Modifier.size(24.dp),
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BookPinTheme.colors.bgCanvas),
            contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding() + 80.dp),
        ) {
            item {
                BookDetailHeader(
                    book = state.book,
                    onBackClick = viewModel::onBackClick,
                )
            }

            item {
                BookDetailStats(
                    currentPage = state.book.currentPage,
                    totalPages = state.book.totalPages,
                    progressPercent = state.book.progressPercent,
                    onMarkAsCompleteClick = viewModel::onMarkAsCompleteClick,
                )
            }

            item {
                BookDetailTabs(
                    selectedTab = state.selectedTab,
                    textCount = viewModel.textBookmarkCount,
                    photoCount = viewModel.photoBookmarkCount,
                    onTabSelected = viewModel::onTabSelected,
                )
            }

            val filteredBookmarks = state.bookmarks.filter { it.type == state.selectedTab }
            if (state.selectedTab == BookmarkTab.TEXT) {
                items(filteredBookmarks, key = { it.id }) { bookmark ->
                    BookmarkItem(bookmark = bookmark)
                }
            } else {
                items(filteredBookmarks.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        rowItems.forEach { bookmark ->
                            PhotoBookmarkItem(
                                bookmark = bookmark,
                                modifier = Modifier.weight(1f),
                            )
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookDetailHeader(
    book: BookDetail,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(192.dp)
            .background(BookPinTheme.colors.accentPrimary),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp),
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = BookPinTheme.colors.bgElevated.copy(alpha = 0.2f),
                        shape = CircleShape,
                    ),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = stringResource(Res.string.cd_back),
                    modifier = Modifier.size(24.dp),
                    tint = BookPinTheme.colors.iconOnAccent,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = book.title,
                style = BookPinTheme.typography.headlineMedium,
                color = BookPinTheme.colors.textOnAccent,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = book.author,
                style = BookPinTheme.typography.bodyMedium,
                color = BookPinTheme.colors.textOnAccent.copy(alpha = 0.8f),
            )
        }
    }
}

@Composable
private fun BookDetailStats(
    currentPage: Int,
    totalPages: Int,
    progressPercent: Int,
    onMarkAsCompleteClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-24).dp)
            .padding(horizontal = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                ).background(
                    color = BookPinTheme.colors.bgElevated,
                    shape = RoundedCornerShape(16.dp),
                ).padding(24.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = "$currentPage",
                        style = BookPinTheme.typography.bodyLarge,
                        color = BookPinTheme.colors.textPrimary,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(Res.string.pages_format, totalPages),
                        style = BookPinTheme.typography.bodyMedium,
                        color = BookPinTheme.colors.textSecondary,
                    )
                }

                Text(
                    text = stringResource(Res.string.progress_format, progressPercent),
                    style = BookPinTheme.typography.titleSmall,
                    color = BookPinTheme.colors.textAccent,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(
                        color = BookPinTheme.colors.bgSurface,
                        shape = RoundedCornerShape(4.dp),
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = progressPercent / 100f)
                        .background(
                            color = BookPinTheme.colors.buttonPrimary,
                            shape = RoundedCornerShape(4.dp),
                        ),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        color = BookPinTheme.colors.bgSurface,
                        shape = RoundedCornerShape(12.dp),
                    ).clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onMarkAsCompleteClick),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(Res.string.mark_as_complete),
                    style = BookPinTheme.typography.titleSmall,
                    color = BookPinTheme.colors.textSecondary,
                )
            }
        }
    }
}

@Composable
private fun BookDetailTabs(
    selectedTab: BookmarkTab,
    textCount: Int,
    photoCount: Int,
    onTabSelected: (BookmarkTab) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-8).dp)
            .padding(horizontal = 24.dp)
            .background(
                color = BookPinTheme.colors.bgSurface,
                shape = RoundedCornerShape(12.dp),
            ).padding(4.dp),
    ) {
        TabItem(
            text = "${stringResource(Res.string.tab_text)} ($textCount)",
            isSelected = selectedTab == BookmarkTab.TEXT,
            onClick = { onTabSelected(BookmarkTab.TEXT) },
            modifier = Modifier.weight(1f),
        )

        TabItem(
            text = "${stringResource(Res.string.tab_photo)} ($photoCount)",
            isSelected = selectedTab == BookmarkTab.PHOTO,
            onClick = { onTabSelected(BookmarkTab.PHOTO) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(36.dp)
            .then(
                if (isSelected) {
                    Modifier.shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(12.dp),
                    )
                } else {
                    Modifier
                },
            ).background(
                color = if (isSelected) BookPinTheme.colors.bgElevated else Color.Transparent,
                shape = RoundedCornerShape(12.dp),
            ).clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = BookPinTheme.typography.titleSmall,
            color = if (isSelected) {
                BookPinTheme.colors.textPrimary
            } else {
                BookPinTheme.colors.textSecondary
            },
        )
    }
}

@Composable
private fun BookmarkItem(
    bookmark: Bookmark,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
            ).background(
                color = BookPinTheme.colors.bgElevated,
                shape = RoundedCornerShape(16.dp),
            ).padding(16.dp),
    ) {
        Text(
            text = stringResource(Res.string.page_format, bookmark.pageNumber),
            style = BookPinTheme.typography.labelMedium,
            color = BookPinTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = bookmark.quote,
            style = BookPinTheme.typography.bodyLarge,
            color = BookPinTheme.colors.textPrimary,
        )

        if (bookmark.memo.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = bookmark.memo,
                style = BookPinTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic,
                ),
                color = BookPinTheme.colors.textSecondary,
            )
        }
    }
}

@Composable
private fun PhotoBookmarkItem(
    bookmark: Bookmark,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
            ).background(
                color = BookPinTheme.colors.bgElevated,
                shape = RoundedCornerShape(16.dp),
            ).border(
                width = 0.5.dp,
                color = BookPinTheme.colors.borderSubtle,
                shape = RoundedCornerShape(16.dp),
            ).clip(RoundedCornerShape(16.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(BookPinTheme.colors.bgSurface),
            contentAlignment = Alignment.Center,
        ) {
            if (bookmark.photoUrl.isNullOrEmpty()) {
                Icon(
                    painter = painterResource(Res.drawable.ic_photo),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = BookPinTheme.colors.iconDefault,
                )
            } else {
                AsyncImage(
                    model = bookmark.photoUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Text(
                text = stringResource(Res.string.page_format, bookmark.pageNumber),
                style = BookPinTheme.typography.labelMedium,
                color = BookPinTheme.colors.textSecondary,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = bookmark.quote,
                style = BookPinTheme.typography.bodyMedium,
                color = BookPinTheme.colors.textPrimary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )

            if (bookmark.memo.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = bookmark.memo,
                    style = BookPinTheme.typography.labelSmall.copy(
                        fontStyle = FontStyle.Italic,
                    ),
                    color = BookPinTheme.colors.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
