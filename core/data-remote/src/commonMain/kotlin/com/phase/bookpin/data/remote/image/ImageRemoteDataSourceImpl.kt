package com.phase.bookpin.data.remote.image

import com.phase.bookpin.data.api.image.ImageRemoteDataSource
import com.phase.bookpin.data.api.image.PresignedUrlResponse
import com.phase.bookpin.data.remote.client.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path

class ImageRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : ImageRemoteDataSource {
    override suspend fun getPresignedUrl(imageExtension: String): Result<PresignedUrlResponse> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1/image/presigned-url")
                parameters.append("imageExtension", imageExtension)
            }
        }

    override suspend fun uploadImage(
        presignedUrl: String,
        imageBytes: ByteArray,
        contentType: String,
    ): Result<Unit> = runCatching {
        httpClient.safeRequest<String> {
            method = HttpMethod.Put
            url(presignedUrl)
            contentType(ContentType.parse(contentType))
            setBody(imageBytes)
        }.getOrThrow()
    }
}
