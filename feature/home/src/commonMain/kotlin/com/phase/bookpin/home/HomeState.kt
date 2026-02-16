package com.phase.bookpin.home

data class HomeState(
    val books: List<Book> = emptyList(),
)

data class Book(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val coverImageUrl: String = "",
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val bookmarkCount: Int = 0,
    val readingDays: Int = 0,
) {
    val progressPercent: Int
        get() = if (totalPages > 0) (currentPage * 100 / totalPages) else 0
}
