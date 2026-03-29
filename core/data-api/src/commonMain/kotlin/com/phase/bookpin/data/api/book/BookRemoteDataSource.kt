package com.phase.bookpin.data.api.book

interface BookRemoteDataSource {
    suspend fun getBookItems(): Result<List<BookItemResponse>>
    suspend fun getBookDetail(bookId: Long): Result<BookDetailResponse>
    suspend fun getTextBookmarks(bookId: Long): Result<List<BookmarkResponse>>
    suspend fun getPhotoBookmarks(bookId: Long): Result<List<BookmarkResponse>>
}
