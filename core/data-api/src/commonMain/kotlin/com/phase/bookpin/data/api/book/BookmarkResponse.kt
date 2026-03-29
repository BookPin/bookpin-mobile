package com.phase.bookpin.data.api.book

import com.phase.bookpin.model.book.Bookmark
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkResponse(
    val id: Long,
    val bookId: Long,
    val pageNumber: Int = 0,
    val extractedText: String = "",
    val note: String = "",
    val imageUrl: String = "",
    val createdAt: String = "",
) {
    fun toBookmark(): Bookmark = Bookmark(
        id = id,
        bookId = bookId,
        pageNumber = pageNumber,
        extractedText = extractedText,
        note = note,
        imageUrl = imageUrl,
    )
}
