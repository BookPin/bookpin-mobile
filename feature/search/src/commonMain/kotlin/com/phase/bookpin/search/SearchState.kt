package com.phase.bookpin.search

data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val searchResults: List<SearchBook> = emptyList(),
    val hasSearched: Boolean = false,
)

data class SearchBook(
    val id: String,
    val title: String,
    val author: String,
    val coverImageUrl: String,
)
