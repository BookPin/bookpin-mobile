package com.phase.bookpin.search

import com.phase.bookpin.model.search.BookSearchResult

data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val searchResults: List<BookSearchResult> = emptyList(),
    val hasSearched: Boolean = false,
)
