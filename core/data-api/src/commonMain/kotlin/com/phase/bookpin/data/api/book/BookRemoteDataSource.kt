package com.phase.bookpin.data.api.book

interface BookRemoteDataSource {
    suspend fun getBookItems(): Result<List<BookItemResponse>>

    suspend fun getBookDetail(bookId: Long): Result<BookDetailResponse>

    suspend fun getLatestBookmark(): Result<LatestBookmarkResponse?>

    suspend fun getTextBookmarks(bookId: Long): Result<List<BookmarkResponse>>

    suspend fun getPhotoBookmarks(bookId: Long): Result<List<BookmarkResponse>>

    suspend fun addBookmark(bookId: Long, request: AddBookmarkRequest): Result<BookmarkResponse>

    suspend fun completeBook(bookId: Long): Result<BookDetailResponse>

    suspend fun deleteBookmark(bookId: Long, bookmarkId: Long): Result<Unit>
}
