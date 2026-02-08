package com.phase.bookpin.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import bookpin.home.generated.resources.*
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = LocalSnackbarHost.current

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is HomeSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(it.message)
            }
            HomeSideEffect.NavigateToSettings -> {}
            is HomeSideEffect.NavigateToBookDetail -> {}
            HomeSideEffect.NavigateToAddBook -> {}
            is HomeSideEffect.NavigateToAddBookmark -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BookPinTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp),
    ) {
        HomeTopBar(onSettingsClick = viewModel::onSettingsClick)

        Spacer(modifier = Modifier.height(32.dp))

        CurrentlyReadingSection(
            book = state.books.firstOrNull(),
            onAddBookmarkClick = { state.books.firstOrNull()?.let { viewModel.onAddBookmarkClick(it.id) } },
            onAddBookClick = viewModel::onAddBookClick,
        )

        Spacer(modifier = Modifier.height(32.dp))

        BookShelfSection(
            books = state.books,
            onAddBookClick = viewModel::onAddBookClick,
            onBookClick = viewModel::onBookClick,
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun HomeTopBar(
    onSettingsClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
    val colorIndex = book.id.hashCode().let { kotlin.math.abs(it) % coverColors.size }

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

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onAddBookmarkClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(16.dp),
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BookPinTheme.colors.onSurface,
                        contentColor = Color.White,
                    ),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.leave_bookmark),
                        style = BookPinTheme.typography.labelMedium.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
private fun BookShelfSection(
    books: List<Book>,
    onAddBookClick: () -> Unit,
    onBookClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(Res.string.my_bookshelf),
            style = BookPinTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Medium,
            ),
            color = BookPinTheme.colors.onSurface,
        )

        if (books.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .background(
                        color = BookPinTheme.colors.surfaceVariant,
                        shape = CircleShape,
                    ).clickable(onClick = onAddBookClick)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.add),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = BookPinTheme.colors.onSurface,
                )

                Text(
                    text = stringResource(Res.string.add_book),
                    style = BookPinTheme.typography.titleMedium,
                    color = BookPinTheme.colors.onSurface,
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (books.isNotEmpty()) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
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
    val colorIndex = book.id.hashCode().let { kotlin.math.abs(it) % coverColors.size }

    Column(
        modifier = Modifier
            .width(165.dp)
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
            Row(verticalAlignment = Alignment.Bottom) {
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
                    tint = BookPinTheme.colors.onSurface.copy(alpha = 0.8f),
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
