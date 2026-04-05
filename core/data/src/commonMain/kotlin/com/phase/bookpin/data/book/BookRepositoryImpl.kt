package com.phase.bookpin.data.book

import com.phase.bookpin.data.api.book.BookRemoteDataSource
import com.phase.bookpin.data.api.book.AddBookmarkRequest
import com.phase.bookpin.domain.book.BookRepository
import com.phase.bookpin.model.book.BookDetail
import com.phase.bookpin.model.book.BookItem
import com.phase.bookpin.model.book.Bookmark
import com.phase.bookpin.model.book.LatestBookmark

class BookRepositoryImpl(
    private val dataSource: BookRemoteDataSource,
) : BookRepository {
    override suspend fun getBookItems(): Result<List<BookItem>> {
        return dataSource.getBookItems().mapCatching { responses ->
            responses.map { it.toBookItem() }
        }
    }

    override suspend fun getBookDetail(bookId: Long): Result<BookDetail> {
        return dataSource.getBookDetail(bookId).mapCatching { it.toBookDetail() }
    }

    override suspend fun getLatestBookmark(): Result<LatestBookmark?> {
        return dataSource.getLatestBookmark().mapCatching { it?.toLatestBookmark() }
    }

    override suspend fun getTextBookmarks(bookId: Long): Result<List<Bookmark>> {
        return dataSource.getTextBookmarks(bookId).mapCatching { responses ->
            responses.map { it.toBookmark() }
        }
    }

    override suspend fun getPhotoBookmarks(bookId: Long): Result<List<Bookmark>> {
        return dataSource.getPhotoBookmarks(bookId).mapCatching { responses ->
            responses.map { it.toBookmark() }
        }
    }

    override suspend fun addBookmark(
        bookId: Long,
        pageNumber: Int,
        extractedText: String,
        note: String,
        imageUrl: String?,
    ): Result<Bookmark> {
        val request = AddBookmarkRequest(
            pageNumber = pageNumber,
            extractedText = extractedText,
            note = note,
            imageUrl = imageUrl,
        )
        return dataSource.addBookmark(bookId, request).mapCatching { it.toBookmark() }
    }

    override suspend fun completeBook(bookId: Long): Result<BookDetail> {
        return dataSource.completeBook(bookId).mapCatching { it.toBookDetail() }
    }

    override suspend fun deleteBookmark(bookId: Long, bookmarkId: Long): Result<Unit> {
        return dataSource.deleteBookmark(bookId, bookmarkId)
    }
}
