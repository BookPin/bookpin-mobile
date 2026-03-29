package com.phase.bookpin.data.api.book

import com.phase.bookpin.model.book.BookDetail
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailResponse(
    val id: Long,
    val title: String = "",
    val author: String = "",
    val imageUrl: String = "",
    val totalPage: Int = 0,
    val currentPage: Int = 0,
    val progress: Double = 0.0,
    val isCompleted: Boolean = false,
    val createdAt: String = "",
) {
    fun toBookDetail(): BookDetail = BookDetail(
        id = id,
        title = title,
        author = author,
        imageUrl = imageUrl,
        totalPage = totalPage,
        currentPage = currentPage,
        progress = progress,
        isCompleted = isCompleted,
    )
}
