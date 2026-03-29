package com.phase.bookpin.data.image

import com.phase.bookpin.data.api.image.ImageRemoteDataSource
import com.phase.bookpin.domain.image.ImageRepository

class ImageRepositoryImpl(
    private val dataSource: ImageRemoteDataSource,
) : ImageRepository {
    override suspend fun getImageUrl(imageExtension: String): Result<String> =
        dataSource.getPresignedUrl(imageExtension).mapCatching { it.imageUrl }
}
