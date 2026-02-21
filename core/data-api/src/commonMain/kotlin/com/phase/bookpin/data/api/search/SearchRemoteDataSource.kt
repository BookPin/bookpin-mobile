package com.phase.bookpin.data.api.search

interface SearchRemoteDataSource {
    suspend fun searchBooks(query: String): Result<List<BookSearchResultResponse>>
    suspend fun addBook(request: AddBookRequest): Result<Unit>
}
