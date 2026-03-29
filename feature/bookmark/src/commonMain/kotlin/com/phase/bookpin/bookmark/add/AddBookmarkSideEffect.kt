package com.phase.bookpin.bookmark.add

sealed interface AddBookmarkSideEffect {
    data class ShowSnackbar(
        val message: String,
    ) : AddBookmarkSideEffect
    data object NavigateBack : AddBookmarkSideEffect
    data object BookmarkSaved : AddBookmarkSideEffect
}
