package com.phase.bookpin.model.book

data class BookItem(
    val id: Long,
    val title: String,
    val author: String,
    val imageUrl: String,
    val totalPage: Int,
    val currentPage: Int,
    val progress: Double,
    val bookmarkCount: Int,
) {
    val isCompleted: Boolean = totalPage == currentPage
}
