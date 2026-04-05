package com.phase.bookpin.data.remote.book

import com.phase.bookpin.data.api.book.BookDetailResponse
import com.phase.bookpin.data.api.book.BookItemResponse
import com.phase.bookpin.data.api.book.BookRemoteDataSource
import com.phase.bookpin.data.api.book.BookmarkResponse
import com.phase.bookpin.data.api.book.CreateBookmarkRequest
import com.phase.bookpin.data.api.book.LatestBookmarkResponse
import com.phase.bookpin.data.remote.client.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class BookRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : BookRemoteDataSource {
    override suspend fun getBookItems(): Result<List<BookItemResponse>> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1/books")
            }
        }

    override suspend fun getBookDetail(bookId: Long): Result<BookDetailResponse> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1/books/$bookId")
            }
        }

    override suspend fun getLatestBookmark(): Result<LatestBookmarkResponse?> =
        httpClient.safeRequest<LatestBookmarkResponse?> {
            url {
                method = HttpMethod.Get
                path("api/v1/books/latest-bookmark")
            }
        }

    override suspend fun getTextBookmarks(bookId: Long): Result<List<BookmarkResponse>> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1/books/$bookId/bookmarks/text")
            }
        }

    override suspend fun getPhotoBookmarks(bookId: Long): Result<List<BookmarkResponse>> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1/books/$bookId/bookmarks/photo")
            }
        }

    override suspend fun createBookmark(
        bookId: Long,
        request: CreateBookmarkRequest,
    ): Result<BookmarkResponse> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1/books/$bookId/bookmarks")
            }
            setBody(request)
        }

    override suspend fun completeBook(bookId: Long): Result<BookDetailResponse> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1/books/$bookId/complete")
            }
        }

    override suspend fun deleteBookmark(bookId: Long, bookmarkId: Long): Result<Unit> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Delete
                path("api/v1/books/$bookId/bookmarks/$bookmarkId")
            }
        }
}
