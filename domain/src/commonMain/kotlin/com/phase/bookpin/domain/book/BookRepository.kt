package com.phase.bookpin.domain.book

import com.phase.bookpin.model.book.BookDetail
import com.phase.bookpin.model.book.BookItem
import com.phase.bookpin.model.book.Bookmark

interface BookRepository {
    suspend fun getBookItems(): Result<List<BookItem>>
    suspend fun getBookDetail(bookId: Long): Result<BookDetail>
    suspend fun getTextBookmarks(bookId: Long): Result<List<Bookmark>>
    suspend fun getPhotoBookmarks(bookId: Long): Result<List<Bookmark>>
}
