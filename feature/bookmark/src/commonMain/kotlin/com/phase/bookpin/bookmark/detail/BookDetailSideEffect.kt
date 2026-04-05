package com.phase.bookpin.bookmark.detail

import com.phase.bookpin.model.book.Bookmark

sealed interface BookDetailSideEffect {
    data class ShowSnackbar(
        val message: String,
    ) : BookDetailSideEffect
    data object NavigateBack : BookDetailSideEffect
    data object NavigateToAddBookmark : BookDetailSideEffect
    data class NavigateToBookmarkDetail(
        val bookmark: Bookmark,
    ) : BookDetailSideEffect
    data object NavigateToHome : BookDetailSideEffect
}
