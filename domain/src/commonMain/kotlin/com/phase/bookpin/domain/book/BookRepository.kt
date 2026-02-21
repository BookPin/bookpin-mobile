package com.phase.bookpin.domain.book

import com.phase.bookpin.model.book.BookItem

interface BookRepository {
    suspend fun getBookItems(): Result<List<BookItem>>
}
