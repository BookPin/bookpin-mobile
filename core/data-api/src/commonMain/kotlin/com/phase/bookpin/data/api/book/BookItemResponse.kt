package com.phase.bookpin.data.api.book

import com.phase.bookpin.model.book.BookItem
import kotlinx.serialization.Serializable

@Serializable
data class BookItemResponse(
    val id: Long,
    val title: String,
    val author: String,
    val imageUrl: String,
    val totalPage: Int,
    val currentPage: Int,
    val progress: Double,
    val bookmarkCount: Int,
) {
    fun toBookItem(): BookItem = BookItem(
        id = id,
        title = title,
        author = author,
        imageUrl = imageUrl,
        totalPage = totalPage,
        currentPage = currentPage,
        progress = progress,
        bookmarkCount = bookmarkCount,
    )
}
