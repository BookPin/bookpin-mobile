package com.phase.bookpin.data.api.auth

import com.phase.bookpin.model.SocialAuthToken
import kotlinx.serialization.Serializable

@Serializable
data class SocialAuthTokenRequest(
    val socialType: String,
    val accessToken: String,
) {
    companion object {
        fun from(model: SocialAuthToken) = SocialAuthTokenRequest(
            socialType = model.type.name,
            accessToken = model.accessToken,
        )
    }
}
