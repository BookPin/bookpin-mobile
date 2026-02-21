package com.phase.bookpin.data.api.search

import kotlinx.serialization.Serializable

@Serializable
data class AddBookRequest(
    val title: String,
    val author: String,
    val imageUrl: String,
    val totalPage: Int,
)
