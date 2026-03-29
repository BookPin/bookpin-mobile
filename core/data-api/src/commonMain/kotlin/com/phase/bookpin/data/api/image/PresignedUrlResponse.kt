package com.phase.bookpin.data.api.image

import kotlinx.serialization.Serializable

@Serializable
data class PresignedUrlResponse(
    val url: String,
    val imageUrl: String,
)
