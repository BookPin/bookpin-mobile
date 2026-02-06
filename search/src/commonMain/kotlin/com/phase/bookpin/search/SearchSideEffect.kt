package com.phase.bookpin.search

sealed interface SearchSideEffect {
    data object NavigateBack : SearchSideEffect
    data object NavigateToManualInput : SearchSideEffect
    data class NavigateToBookDetail(
        val bookId: String,
    ) : SearchSideEffect
    data class ShowSnackbar(
        val message: String,
    ) : SearchSideEffect
}
