package com.phase.bookpin.domain.search

import com.phase.bookpin.model.search.BookSearchResult

interface SearchRepository {
    suspend fun searchBooks(query: String): Result<List<BookSearchResult>>
}
