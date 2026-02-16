package com.phase.bookpin.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.designsystem.generated.resources.bookpin_icon
import bookpin.home.generated.resources.*
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import bookpin.designsystem.generated.resources.Res as DesignRes

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToSearch: () -> Unit = {},
    onNavigateToBookDetail: (String) -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = LocalSnackbarHost.current

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is HomeSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(it.message)
            }
            HomeSideEffect.NavigateToSettings -> onNavigateToSettings()
            is HomeSideEffect.NavigateToBookDetail -> onNavigateToBookDetail(it.bookId)
            HomeSideEffect.NavigateToAddBook -> onNavigateToSearch()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BookPinTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp),
    ) {
        HomeTopBar(onSettingsClick = viewModel::onSettingsClick)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.tagline),
            style = BookPinTheme.typography.bodyMedium,
            color = BookPinTheme.colors.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(32.dp))

        DailyQuoteCard(dailyQuote = state.dailyQuote)

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(7.dp)),
                painter = painterResource(DesignRes.drawable.bookpin_icon),
                contentDescription = stringResource(Res.string.cd_bookpin_icon),
                contentScale = ContentScale.Crop,
            )

            Text(
                text = stringResource(Res.string.app_name),
                style = BookPinTheme.typography.displaySmall.copy(
                    fontSize = 30.sp,
                ),
                color = BookPinTheme.colors.onSecondary,
            )
        }

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
private fun DailyQuoteCard(
    dailyQuote: DailyQuote,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
            ).background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BookPinTheme.colors.surfaceVariant,
                        Color(0xFFDCC7A8),
                    ),
                ),
                shape = RoundedCornerShape(16.dp),
            ).padding(24.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.5f),
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.quote),
                    contentDescription = stringResource(Res.string.cd_quote),
                    modifier = Modifier.size(20.dp),
                    tint = BookPinTheme.colors.onSurface,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(Res.string.daily_quote_label),
                    style = BookPinTheme.typography.bodySmall,
                    color = BookPinTheme.colors.onSurface.copy(alpha = 0.8f),
                )

                Text(
                    text = dailyQuote.text,
                    style = BookPinTheme.typography.bodyLarge,
                    color = BookPinTheme.colors.onSurface,
                )

                Text(
                    text = dailyQuote.author,
                    style = BookPinTheme.typography.bodyMedium,
                    color = Color(0xFF8B7355),
                )
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

    Spacer(modifier = Modifier.height(16.dp))

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
