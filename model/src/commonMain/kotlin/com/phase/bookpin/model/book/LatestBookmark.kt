package com.phase.bookpin.model.book

data class LatestBookmark(
    val id: Long,
    val bookId: Long,
    val pageNumber: Int,
    val extractedText: String,
    val note: String,
    val imageUrl: String,
    val createdAt: String,
)
