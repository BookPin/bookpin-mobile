package com.phase.bookpin.domain.image

interface ImageRepository {
    suspend fun uploadImage(imageBytes: ByteArray, imageExtension: String): Result<String>
}
