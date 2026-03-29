package com.phase.bookpin.bookmark.add

import com.phase.bookpin.model.bookmark.BookmarkType

data class AddBookmarkState(
    val bookmarkType: BookmarkType = BookmarkType.TEXT,
    val photoUri: String? = null,
    val extractedText: String = "",
    val pageNumber: String = "",
    val personalMemo: String = "",
    val isLoading: Boolean = false,
    val isOcrProcessing: Boolean = false,
) {
    val isSaveEnabled: Boolean get() = pageNumber.toIntOrNull() != null
}
