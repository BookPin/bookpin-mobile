package com.phase.bookpin.data.auth.kakao

import android.content.Context
import co.touchlab.kermit.Logger
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.phase.bookpin.domain.kakao.KakaoAuth
import com.phase.bookpin.model.SocialAuthToken
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidKakaoAuth(
    private val context: Context,
) : KakaoAuth {
    override suspend fun loginWithKakao(): Result<SocialAuthToken> = runCatching {
        val loginState = getKakaoLoginState(context)

        when (loginState) {
            KaKaoLoginState.KAKAO_TALK_LOGIN -> {
                val token = UserApiClient.loginWithKakaoTalk(context)
                SocialAuthToken(
                    type = SocialAuthToken.SocialAuthType.KAKAO,
                    accessToken = token.accessToken,
                )
            }

            KaKaoLoginState.KAKAO_ACCOUNT_LOGIN -> {
                val token = UserApiClient.loginWithKakaoAccount(context)
                SocialAuthToken(
                    type = SocialAuthToken.SocialAuthType.KAKAO,
                    accessToken = token.accessToken,
                )
            }
        }
    }.recoverCatching { error ->
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
            throw error
        }
        val token = UserApiClient.loginWithKakaoAccount(context)
        SocialAuthToken(
            type = SocialAuthToken.SocialAuthType.KAKAO,
            accessToken = token.accessToken,
        )
    }

    private suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken =
        suspendCancellableCoroutine { continuation ->
            instance.loginWithKakaoTalk(context) { token, error ->
                continuation.resumeTokenOrException(token, error)
            }
        }

    private suspend fun UserApiClient.Companion.loginWithKakaoAccount(
        context: Context,
    ): OAuthToken = suspendCancellableCoroutine { continuation ->
        instance.loginWithKakaoAccount(context) { token, error ->
            continuation.resumeTokenOrException(token, error)
        }
    }

    private fun getKakaoLoginState(context: Context): KaKaoLoginState =
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            KaKaoLoginState.KAKAO_TALK_LOGIN
        } else {
            KaKaoLoginState.KAKAO_ACCOUNT_LOGIN
        }

    private fun Continuation<OAuthToken>.resumeTokenOrException(
        token: OAuthToken?,
        error: Throwable?,
    ) {
        if (error != null) {
            resumeWithException(error)
        } else if (token != null) {
            resume(token)
        } else {
            resumeWithException(RuntimeException("Failed to get kakao access token, reason is not clear."))
        }
    }

    override fun logout() = UserApiClient.instance.logout { error ->
        if (error != null) {
            Logger.e("Kakao 로그아웃 실패. SDK에서 토큰 삭제됨 : $error")
        } else {
            Logger.d("Kakao 로그아웃 성공. SDK에서 토큰 삭제됨")
        }
    }

    override fun revoke() = UserApiClient.instance.unlink { error ->
        if (error != null) {
            Logger.e("Kakao 탈퇴 실패 : $error")
        } else {
            Logger.d("Kakao 탈퇴 성공. SDK에서 토큰 삭제됨")
        }
    }
}