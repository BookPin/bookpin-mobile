package com.phase.bookpin.bookmark

import com.phase.bookpin.model.book.BookDetail
import com.phase.bookpin.model.book.Bookmark

data class BookDetailState(
    val book: BookDetail = BookDetail(),
    val textBookmarks: List<Bookmark> = emptyList(),
    val photoBookmarks: List<Bookmark> = emptyList(),
    val selectedTab: BookmarkTab = BookmarkTab.TEXT,
    val isLoading: Boolean = false,
)

enum class BookmarkTab {
    TEXT,
    PHOTO,
}
