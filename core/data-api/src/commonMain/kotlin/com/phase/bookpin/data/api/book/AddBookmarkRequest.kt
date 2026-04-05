package com.phase.bookpin.data.api.book

import kotlinx.serialization.Serializable

@Serializable
data class AddBookmarkRequest(
    val pageNumber: Int,
    val extractedText: String,
    val note: String,
    val imageUrl: String?,
)
