package com.phase.bookpin.data.search

import com.phase.bookpin.data.api.search.SearchRemoteDataSource
import com.phase.bookpin.domain.search.SearchRepository
import com.phase.bookpin.model.search.BookSearchResult

class SearchRepositoryImpl(
    private val dataSource: SearchRemoteDataSource,
) : SearchRepository {
    override suspend fun searchBooks(query: String): Result<List<BookSearchResult>> {
        return dataSource.searchBooks(query).mapCatching { responses ->
            responses.map {
                it.toBookSearchResult()
            }
        }
    }
}
