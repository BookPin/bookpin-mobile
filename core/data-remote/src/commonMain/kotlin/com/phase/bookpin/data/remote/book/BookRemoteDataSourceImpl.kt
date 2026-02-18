package com.phase.bookpin.data.remote.book

import com.phase.bookpin.data.api.book.BookItemResponse
import com.phase.bookpin.data.api.book.BookRemoteDataSource
import com.phase.bookpin.data.remote.client.safeRequest
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.path

class BookRemoteDataSourceImpl(
    private val httpClient: HttpClient,
): BookRemoteDataSource {
    override suspend fun getBookItems(): Result<List<BookItemResponse>> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1/books")
            }
        }
}
