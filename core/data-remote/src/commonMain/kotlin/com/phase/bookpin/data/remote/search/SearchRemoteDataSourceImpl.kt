package com.phase.bookpin.data.remote.search

import com.phase.bookpin.data.api.search.BookSearchResultResponse
import com.phase.bookpin.data.api.search.SearchRemoteDataSource
import com.phase.bookpin.data.remote.client.safeRequest
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.path

class SearchRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : SearchRemoteDataSource {
    override suspend fun searchBooks(query: String): Result<List<BookSearchResultResponse>> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1/books/search")
                parameters.append("query", query)
            }
        }
}
