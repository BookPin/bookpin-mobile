package com.phase.bookpin.data.api.image

interface ImageRemoteDataSource {
    suspend fun getPresignedUrl(imageExtension: String): Result<PresignedUrlResponse>
}
