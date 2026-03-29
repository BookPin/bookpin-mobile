package com.phase.bookpin.bookmark.detail

sealed interface BookDetailSideEffect {
    data class ShowSnackbar(
        val message: String,
    ) : BookDetailSideEffect
    data object NavigateBack : BookDetailSideEffect
    data object NavigateToAddBookmark : BookDetailSideEffect
}
