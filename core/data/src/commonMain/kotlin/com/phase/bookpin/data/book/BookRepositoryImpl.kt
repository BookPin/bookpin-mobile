package com.phase.bookpin.data.book

import com.phase.bookpin.data.api.book.BookRemoteDataSource
import com.phase.bookpin.domain.book.BookRepository
import com.phase.bookpin.model.book.BookItem

class BookRepositoryImpl(
    private val dataSource: BookRemoteDataSource,
): BookRepository {
    override suspend fun getBookItems(): Result<List<BookItem>> {
        return dataSource.getBookItems().mapCatching { responses ->
            responses.map {
                it.toBookItem()
            }
        }
    }
}
