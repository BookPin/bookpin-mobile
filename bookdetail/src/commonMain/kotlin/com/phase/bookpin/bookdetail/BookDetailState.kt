package com.phase.bookpin.bookdetail

data class BookDetailState(
    val book: BookDetail = BookDetail(),
    val bookmarks: List<Bookmark> = emptyList(),
    val selectedTab: BookmarkTab = BookmarkTab.TEXT,
)

data class BookDetail(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val coverImageUrl: String = "",
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val bookmarkCount: Int = 0,
    val isCompleted: Boolean = false,
) {
    val progressPercent: Int
        get() = if (totalPages > 0) (currentPage * 100 / totalPages) else 0
}

data class Bookmark(
    val id: String = "",
    val pageNumber: Int = 0,
    val quote: String = "",
    val memo: String = "",
    val type: BookmarkTab = BookmarkTab.TEXT,
    val photoUrl: String? = null,
)

enum class BookmarkTab {
    TEXT,
    PHOTO,
}
