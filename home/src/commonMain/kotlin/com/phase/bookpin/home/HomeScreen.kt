package com.phase.bookpin.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.home.generated.resources.Res
import bookpin.home.generated.resources.add
import bookpin.home.generated.resources.add_book
import bookpin.home.generated.resources.app_name
import bookpin.home.generated.resources.bookmark
import bookpin.home.generated.resources.cd_bookmark
import bookpin.home.generated.resources.cd_settings
import bookpin.home.generated.resources.chevron_right
import bookpin.home.generated.resources.currently_reading
import bookpin.home.generated.resources.empty_bookshelf
import bookpin.home.generated.resources.empty_reading_subtitle
import bookpin.home.generated.resources.empty_reading_title
import bookpin.home.generated.resources.leave_bookmark
import bookpin.home.generated.resources.my_bookshelf
import bookpin.home.generated.resources.setting
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToSearch: () -> Unit = {},
    onNavigateToBookDetail: (String) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = LocalSnackbarHost.current

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is HomeSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(it.message)
            }
            HomeSideEffect.NavigateToSettings -> {}
            is HomeSideEffect.NavigateToBookDetail -> onNavigateToBookDetail(it.bookId)
            is HomeSideEffect.NavigateToAddBookmark -> {}
            HomeSideEffect.NavigateToAddBook -> onNavigateToSearch()
        }
    }

    Scaffold(
        topBar = {
            HomeTopBar(onSettingsClick = viewModel::onSettingsClick)
        },
        floatingActionButton = {
            BookAddButton(
                onClick = viewModel::onAddBookClick,
            )
        },
        containerColor = BookPinTheme.colors.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
        ) {
            CurrentlyReadingSection(
                book = state.books.firstOrNull(),
                onAddBookmarkClick = { state.books.firstOrNull()?.let { viewModel.onAddBookmarkClick(it.id) } },
                onAddBookClick = viewModel::onAddBookClick,
            )

            Spacer(modifier = Modifier.height(32.dp))

            BookShelfSection(
                books = state.books,
                onBookClick = viewModel::onBookClick,
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HomeTopBar(
    onSettingsClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(Res.string.app_name),
            style = BookPinTheme.typography.headlineLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = BookPinTheme.colors.onSecondary,
        )

        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = BookPinTheme.colors.surface,
                    shape = CircleShape,
                ),
        ) {
            Icon(
                painter = painterResource(Res.drawable.setting),
                contentDescription = stringResource(Res.string.cd_settings),
                modifier = Modifier.size(20.dp),
                tint = BookPinTheme.colors.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun CurrentlyReadingSection(
    book: Book?,
    onAddBookmarkClick: () -> Unit,
    onAddBookClick: () -> Unit,
) {
    Text(
        text = stringResource(Res.string.currently_reading),
        style = BookPinTheme.typography.headlineMedium.copy(
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
        ),
        color = BookPinTheme.colors.onSurface,
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (book != null) {
        CurrentlyReadingCard(
            book = book,
            onAddBookmarkClick = onAddBookmarkClick,
        )
    } else {
        EmptyReadingCard(onClick = onAddBookClick)
    }
}

@Composable
private fun BookAddButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = BookPinTheme.colors.surfaceVariant,
            contentColor = BookPinTheme.colors.onSurface,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp,
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                painter = painterResource(Res.drawable.add),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
            Text(
                text = stringResource(Res.string.add_book),
                style = BookPinTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
private fun EmptyReadingCard(
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0F6B5744),
                spotColor = Color(0x0F6B5744),
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            width = 0.615.dp,
            color = BookPinTheme.colors.outline,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(Res.string.empty_reading_title),
                    style = BookPinTheme.typography.headlineSmall.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    color = BookPinTheme.colors.onSurface,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(Res.string.empty_reading_subtitle),
                    style = BookPinTheme.typography.titleSmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    color = Color(0xFF8B7355),
                )
            }

            Icon(
                painter = painterResource(Res.drawable.chevron_right),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = BookPinTheme.colors.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun CurrentlyReadingCard(
    book: Book,
    onAddBookmarkClick: () -> Unit,
) {
    val coverColors = listOf(
        BookPinTheme.colors.secondary,
        BookPinTheme.colors.secondaryContainer,
    )
    val colorIndex = book.id.hashCode().absoluteValue % coverColors.size

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color(0x0F6B5744),
                spotColor = Color(0x0F6B5744),
            ).background(
                color = Color.White,
                shape = RoundedCornerShape(28.dp),
            ).border(
                width = 0.615.dp,
                color = Color(0xFFF5EFE6),
                shape = RoundedCornerShape(28.dp),
            ).padding(24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .width(96.dp)
                    .height(144.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp),
                    ).background(
                        color = coverColors[colorIndex],
                        shape = RoundedCornerShape(12.dp),
                    ).clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = book.title.take(2),
                    style = BookPinTheme.typography.headlineLarge,
                    color = Color.White.copy(alpha = 0.5f),
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(144.dp),
            ) {
                Text(
                    text = book.title,
                    style = BookPinTheme.typography.headlineSmall.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    color = BookPinTheme.colors.onSurface,
                    maxLines = 1,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = book.author,
                        style = BookPinTheme.typography.titleSmall.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                        color = BookPinTheme.colors.onSurfaceVariant,
                    )

                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .background(
                                color = BookPinTheme.colors.outline,
                                shape = CircleShape,
                            ),
                    )

                    Text(
                        text = "${book.readingDays}일째",
                        style = BookPinTheme.typography.labelMedium.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                        color = BookPinTheme.colors.primary,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = "${book.currentPage}p / ${book.totalPages}p ",
                        style = BookPinTheme.typography.titleSmall.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = BookPinTheme.colors.onSurfaceVariant,
                    )

                    Text(
                        text = "${book.progressPercent}%",
                        style = BookPinTheme.typography.titleSmall.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = BookPinTheme.colors.primary,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(
                            color = BookPinTheme.colors.surface,
                            shape = CircleShape,
                        ),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(book.progressPercent / 100f)
                            .background(
                                color = BookPinTheme.colors.primary,
                                shape = CircleShape,
                            ),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onAddBookmarkClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BookPinTheme.colors.surfaceVariant,
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 6.dp,
                    ),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.leave_bookmark),
                        style = BookPinTheme.typography.labelMedium.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                        color = BookPinTheme.colors.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
private fun BookShelfSection(
    books: List<Book>,
    onBookClick: (String) -> Unit,
) {
    Text(
        text = stringResource(Res.string.my_bookshelf),
        style = BookPinTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
        ),
        color = BookPinTheme.colors.onSurface,
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (books.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(books, key = { it.id }) { book ->
                BookCard(
                    book = book,
                    onClick = { onBookClick(book.id) },
                )
            }
        }
    } else {
        Text(
            text = stringResource(Res.string.empty_bookshelf),
            style = BookPinTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            ),
            color = BookPinTheme.colors.onSurfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun BookCard(
    book: Book,
    onClick: () -> Unit,
) {
    val coverColors = listOf(
        BookPinTheme.colors.secondary,
        BookPinTheme.colors.secondaryContainer,
    )
    val colorIndex = book.id.hashCode().absoluteValue % coverColors.size

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
            ).background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
            ).clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .background(
                    color = coverColors[colorIndex],
                    shape = RoundedCornerShape(16.dp),
                ).clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = book.title.take(2),
                style = BookPinTheme.typography.headlineLarge,
                color = Color.White.copy(alpha = 0.5f),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = book.title,
            style = BookPinTheme.typography.titleSmall,
            color = BookPinTheme.colors.onSurface,
            maxLines = 1,
        )

        Text(
            text = book.author,
            style = BookPinTheme.typography.labelMedium,
            color = BookPinTheme.colors.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${book.currentPage} / ${book.totalPages} ",
                    style = BookPinTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                    ),
                    color = BookPinTheme.colors.onSurfaceVariant,
                )

                Text(
                    text = "(${book.progressPercent}%)",
                    style = BookPinTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = BookPinTheme.colors.primary,
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
                    text = "${book.bookmarkCount}",
                    style = BookPinTheme.typography.labelMedium,
                    color = BookPinTheme.colors.onSurface.copy(alpha = 0.8f),
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(
                    color = BookPinTheme.colors.surface,
                    shape = CircleShape,
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(book.progressPercent / 100f)
                    .background(
                        color = BookPinTheme.colors.primary,
                        shape = CircleShape,
                    ),
            )
        }
    }
}
