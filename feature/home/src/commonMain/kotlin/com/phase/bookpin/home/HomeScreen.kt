package com.phase.bookpin.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.home.generated.resources.Res
import bookpin.home.generated.resources.currently_reading
import bookpin.home.generated.resources.empty_bookshelf
import bookpin.home.generated.resources.my_bookshelf
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.designsystem.component.BPLoadingScreen
import com.phase.bookpin.home.component.BookAddButton
import com.phase.bookpin.home.component.BookItemCard
import com.phase.bookpin.home.component.CurrentlyReadingCard
import com.phase.bookpin.home.component.HomeTopBar
import com.phase.bookpin.model.book.BookItem
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToSearch: () -> Unit = {},
    onNavigateToBookDetail: (Long) -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = LocalSnackbarHost.current

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is HomeSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(getString(it.message))
            }
            HomeSideEffect.NavigateToSettings -> onNavigateToSettings()
            is HomeSideEffect.NavigateToBookDetail -> onNavigateToBookDetail(it.bookId)
            is HomeSideEffect.NavigateToAddBookmark -> onNavigateToBookDetail(it.bookId)
            HomeSideEffect.NavigateToAddBook -> onNavigateToSearch()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEnterScreen()
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
    ) { innerPadding ->
        if (state.isLoading) {
            BPLoadingScreen(modifier = Modifier.padding(innerPadding))
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            val currentReadingBook = state.bookItems.firstOrNull()
            AnimatedVisibility(currentReadingBook != null) {
                val book = currentReadingBook ?: return@AnimatedVisibility
                CurrentlyReadingSection(
                    bookItem = book,
                    onAddBookmarkClick = { viewModel.onAddBookmarkClick(book.id) },
                )
            }

            BookShelfSection(
                bookItems = state.bookItems,
                onBookClick = viewModel::onBookClick,
            )
        }
    }
}

@Composable
private fun CurrentlyReadingSection(
    bookItem: BookItem,
    onAddBookmarkClick: () -> Unit,
) {
    Column {
        Text(
            text = stringResource(Res.string.currently_reading),
            style = BookPinTheme.typography.titleLarge,
            color = BookPinTheme.colors.textPrimary,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CurrentlyReadingCard(
            bookItem = bookItem,
            onAddBookmarkClick = onAddBookmarkClick,
        )
    }
}

@Composable
private fun BookShelfSection(
    bookItems: List<BookItem>,
    onBookClick: (Long) -> Unit,
) {
    Column {
        Text(
            text = stringResource(Res.string.my_bookshelf),
            style = BookPinTheme.typography.titleLarge,
            color = BookPinTheme.colors.textPrimary,
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (bookItems.isEmpty()) {
            Text(
                text = stringResource(Res.string.empty_bookshelf),
                style = BookPinTheme.typography.titleSmall,
                color = BookPinTheme.colors.textSecondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center,
            )
            return
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(bookItems, key = { it.id }) { bookItem ->
                BookItemCard(
                    bookItem = bookItem,
                    onClick = { onBookClick(bookItem.id) },
                )
            }
        }
    }
}
