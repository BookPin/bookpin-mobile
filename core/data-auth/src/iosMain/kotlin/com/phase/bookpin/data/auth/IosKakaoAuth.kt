package com.phase.bookpin.data.auth.com.phase.bookpin.data.auth

import com.phase.bookpin.domain.kakao.KakaoAuth
import com.phase.bookpin.model.SocialAuthToken

class IosKakaoAuth: KakaoAuth {
    override suspend fun loginWithKakao(): Result<SocialAuthToken> {
        return Result.failure(UnsupportedOperationException())
    }

    override fun logout() = Unit

    override fun revoke() = Unit
}
