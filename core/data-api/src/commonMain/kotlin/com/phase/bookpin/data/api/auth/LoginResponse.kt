package com.phase.bookpin.data.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean,
)
