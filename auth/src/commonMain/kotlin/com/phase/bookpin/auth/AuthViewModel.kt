package com.phase.bookpin.auth

import androidx.lifecycle.viewModelScope
import bookpin.auth.generated.resources.Res
import bookpin.auth.generated.resources.retry
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.auth.AuthRepository
import com.phase.bookpin.domain.kakao.KakaoAuth
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
    private val kakaoAuth: KakaoAuth,
) : BaseViewModel<AuthState, AuthSideEffect>() {
    override fun createInitialState(): AuthState = AuthState()

    fun loginWithKakao() {
        reduce { state.copy(isLoading = true) }
        viewModelScope.launch {
            val kakaoToken = kakaoAuth.loginWithKakao().getOrElse {
                postSideEffect(AuthSideEffect.ShowSnackbar(Res.string.retry))
                reduce { state.copy(isLoading = false) }
                return@launch
            }

            repository
                .login(kakaoToken)
                .onSuccess {
                    postSideEffect(AuthSideEffect.NavigateToHome)
                }.onFailure {
                    postSideEffect(AuthSideEffect.ShowSnackbar(Res.string.retry))
                }.also {
                    reduce { state.copy(isLoading = false) }
                }
        }
    }
}
