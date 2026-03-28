package com.phase.bookpin.search

import com.phase.bookpin.model.search.BookSearchResult

sealed interface SearchSideEffect {
    data object NavigateBack : SearchSideEffect
    data object NavigateToManualInput : SearchSideEffect
    data class NavigateToBookPreview(
        val result: BookSearchResult,
    ) : SearchSideEffect
    data class ShowSnackbar(
        val message: String,
    ) : SearchSideEffect
}
