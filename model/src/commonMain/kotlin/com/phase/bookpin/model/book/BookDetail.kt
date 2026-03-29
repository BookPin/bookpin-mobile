package com.phase.bookpin.model.book

data class BookDetail(
    val id: Long,
    val title: String,
    val author: String,
    val imageUrl: String,
    val totalPage: Int,
    val currentPage: Int,
    val progress: Double,
    val isCompleted: Boolean,
) {
    companion object {
        val EMPTY = BookDetail(
            id = 0,
            title = "",
            author = "",
            imageUrl = "",
            totalPage = 0,
            currentPage = 0,
            progress = 0.0,
            isCompleted = false,
        )
    }
}
