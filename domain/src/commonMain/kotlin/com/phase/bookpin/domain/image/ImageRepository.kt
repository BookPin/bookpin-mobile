package com.phase.bookpin.domain.image

interface ImageRepository {
    suspend fun getImageUrl(imageExtension: String): Result<String>
}
