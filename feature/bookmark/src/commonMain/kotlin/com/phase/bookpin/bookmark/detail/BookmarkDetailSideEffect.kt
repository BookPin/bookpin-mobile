package com.phase.bookpin.bookmark.detail

sealed interface BookmarkDetailSideEffect {
    data class ShowSnackbar(
        val message: String,
    ) : BookmarkDetailSideEffect
    data object NavigateBack : BookmarkDetailSideEffect
}
