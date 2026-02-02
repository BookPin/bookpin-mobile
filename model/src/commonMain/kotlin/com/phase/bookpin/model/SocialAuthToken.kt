package com.phase.bookpin.model

data class SocialAuthToken(
    val type: SocialAuthType,
    val accessToken: String,
) {
    enum class SocialAuthType {
        KAKAO,
    }
}
