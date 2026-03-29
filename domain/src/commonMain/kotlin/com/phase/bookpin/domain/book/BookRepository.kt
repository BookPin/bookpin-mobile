package com.phase.bookpin.domain.book

import com.phase.bookpin.model.book.BookItem
import com.phase.bookpin.model.book.LatestBookmark

interface BookRepository {
    suspend fun getBookItems(): Result<List<BookItem>>

    suspend fun getLatestBookmark(): Result<LatestBookmark?>
}
