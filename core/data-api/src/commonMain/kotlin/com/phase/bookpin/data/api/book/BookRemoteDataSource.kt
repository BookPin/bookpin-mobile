package com.phase.bookpin.data.api.book

interface BookRemoteDataSource {
    suspend fun getBookItems(): Result<List<BookItemResponse>>
}
