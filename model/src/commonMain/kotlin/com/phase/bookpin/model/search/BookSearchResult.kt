package com.phase.bookpin.model.search

data class BookSearchResult(
    val title: String,
    val author: String,
    val imageUrl: String,
    val totalPage: Int,
    val isbn: String,
    val publisher: String,
    val source: String,
)
