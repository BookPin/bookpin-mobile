package com.phase.bookpin.domain.kakao

import com.phase.bookpin.model.SocialAuthToken

interface KakaoAuth {
    suspend fun loginWithKakao(): Result<SocialAuthToken>

    fun logout()

    fun revoke()
}