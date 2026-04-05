package com.phase.bookpin.domain.book

import com.phase.bookpin.model.book.BookDetail
import com.phase.bookpin.model.book.BookItem
import com.phase.bookpin.model.book.Bookmark
import com.phase.bookpin.model.book.LatestBookmark

interface BookRepository {
    suspend fun getBookItems(): Result<List<BookItem>>

    suspend fun getBookDetail(bookId: Long): Result<BookDetail>

    suspend fun getLatestBookmark(): Result<LatestBookmark?>

    suspend fun getTextBookmarks(bookId: Long): Result<List<Bookmark>>

    suspend fun getPhotoBookmarks(bookId: Long): Result<List<Bookmark>>

    suspend fun createBookmark(
        bookId: Long,
        pageNumber: Int,
        extractedText: String,
        note: String,
        imageUrl: String,
    ): Result<Bookmark>

    suspend fun completeBook(bookId: Long): Result<BookDetail>

    suspend fun deleteBookmark(bookId: Long, bookmarkId: Long): Result<Unit>
}
