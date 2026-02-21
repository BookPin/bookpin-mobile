package com.phase.bookpin.data.api.search

import com.phase.bookpin.model.search.BookSearchResult
import kotlinx.serialization.Serializable

@Serializable
data class BookSearchResultResponse(
    val title: String,
    val author: String = "",
    val imageUrl: String = "",
    val totalPage: Int = -1,
    val isbn: String,
    val publisher: String = "",
    val source: String,
) {
    fun toBookSearchResult(): BookSearchResult = BookSearchResult(
        title = title,
        author = author,
        imageUrl = imageUrl,
        totalPage = totalPage,
        isbn = isbn,
        publisher = publisher,
        source = source,
    )
}
