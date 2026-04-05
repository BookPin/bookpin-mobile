package com.phase.bookpin.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.search.generated.resources.*
import coil3.compose.AsyncImage
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.designsystem.component.BPTextField
import com.phase.bookpin.model.search.BookSearchResult
import com.phase.bookpin.search.component.SearchTopBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToManualInput: () -> Unit = {},
    onNavigateToBookPreview: (BookSearchResult) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = LocalSnackbarHost.current

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is SearchSideEffect.ShowSnackbar -> snackbarHost.showSnackbar(it.message)
            is SearchSideEffect.NavigateToBookPreview -> onNavigateToBookPreview(it.result)
            SearchSideEffect.NavigateBack -> onNavigateBack()
            SearchSideEffect.NavigateToManualInput -> onNavigateToManualInput()
        }
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                onCloseClick = viewModel::onCloseClick,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(innerPadding),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            BPTextField(
                value = state.query,
                onValueChange = viewModel::onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(Res.string.search_placeholder),
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = stringResource(Res.string.cd_search),
                        modifier = Modifier.size(24.dp),
                        tint = BookPinTheme.colors.iconDefault,
                    )
                },
            )

            if (state.isLoading) {
                Spacer(modifier = Modifier.height(96.dp))
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = BookPinTheme.colors.buttonPrimary,
                    trackColor = BookPinTheme.colors.bgSurface,
                )
                return@Column
            }

            if (!state.hasSearched) {
                Spacer(modifier = Modifier.height(96.dp))
                SearchEmptyInitial(
                    onManualInputClick = viewModel::onManualInputClick,
                )
                return@Column
            }

            if (state.searchResults.isEmpty()) {
                Spacer(modifier = Modifier.height(96.dp))
                SearchEmptyNoResults(
                    onManualInputClick = viewModel::onManualInputClick,
                )
                return@Column
            }

            Spacer(modifier = Modifier.height(24.dp))
            SearchResultList(
                results = state.searchResults,
                onBookClick = viewModel::onBookClick,
            )
        }
    }
}

@Composable
private fun SearchEmptyInitial(
    onManualInputClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.6f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_book_open),
            contentDescription = stringResource(Res.string.cd_book),
            modifier = Modifier.size(64.dp),
            tint = BookPinTheme.colors.iconDefault,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.search_empty_initial),
            style = BookPinTheme.typography.headlineSmall,
            color = BookPinTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.search_manual_input_link),
            style = BookPinTheme.typography.titleMedium.copy(
                textDecoration = TextDecoration.Underline,
            ),
            color = BookPinTheme.colors.textAccent,
            modifier = Modifier.clickable(onClick = onManualInputClick),
        )
    }
}

@Composable
private fun SearchEmptyNoResults(
    onManualInputClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.search_empty_no_results),
            style = BookPinTheme.typography.headlineSmall,
            color = BookPinTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .background(
                    color = BookPinTheme.colors.bgMuted,
                    shape = RoundedCornerShape(24.dp),
                ).clickable(onClick = onManualInputClick)
                .padding(horizontal = 24.dp, vertical = 12.dp),
        ) {
            Text(
                text = stringResource(Res.string.search_manual_input),
                style = BookPinTheme.typography.titleMedium,
                color = BookPinTheme.colors.textPrimary,
            )
        }
    }
}

@Composable
private fun SearchResultList(
    results: List<BookSearchResult>,
    onBookClick: (BookSearchResult) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = results,
            key = { it.isbn },
        ) { book ->
            SearchResultItem(
                book = book,
                onClick = { onBookClick(book) },
            )
        }
    }
}

@Composable
private fun SearchResultItem(
    book: BookSearchResult,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(114.dp)
            .border(
                width = 1.dp,
                color = BookPinTheme.colors.borderSubtle,
                shape = RoundedCornerShape(16.dp),
            ).background(
                color = BookPinTheme.colors.bgElevated,
                shape = RoundedCornerShape(16.dp),
            ).clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = book.imageUrl,
            contentDescription = book.title,
            modifier = Modifier
                .width(56.dp)
                .height(80.dp)
                .shadow(elevation = 4.dp)
                .background(BookPinTheme.colors.bgSurface),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = book.title,
                style = BookPinTheme.typography.titleMedium,
                color = BookPinTheme.colors.textPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = book.author,
                style = BookPinTheme.typography.bodyMedium,
                color = BookPinTheme.colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(Res.drawable.ic_chevron_right),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = BookPinTheme.colors.iconDefault,
        )
    }
}
