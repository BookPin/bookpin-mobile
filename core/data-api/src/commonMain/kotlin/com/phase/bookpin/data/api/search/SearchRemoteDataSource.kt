package com.phase.bookpin.data.api.search

interface SearchRemoteDataSource {
    suspend fun searchBooks(query: String): Result<List<BookSearchResultResponse>>
}
