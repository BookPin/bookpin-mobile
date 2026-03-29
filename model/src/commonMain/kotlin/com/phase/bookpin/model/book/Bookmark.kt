package com.phase.bookpin.model.book

data class Bookmark(
    val id: Long = 0,
    val bookId: Long = 0,
    val pageNumber: Int = 0,
    val extractedText: String = "",
    val note: String = "",
    val imageUrl: String = "",
)
