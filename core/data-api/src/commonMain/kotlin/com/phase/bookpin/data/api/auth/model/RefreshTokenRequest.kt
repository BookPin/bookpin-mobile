package com.phase.bookpin.data.api.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(val refreshToken: String)
