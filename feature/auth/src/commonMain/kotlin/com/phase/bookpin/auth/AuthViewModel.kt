package com.phase.bookpin.auth

import androidx.lifecycle.viewModelScope
import bookpin.auth.generated.resources.Res
import bookpin.auth.generated.resources.retry
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.auth.AuthRepository
import com.phase.bookpin.model.auth.DeviceAuthToken
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
) : BaseViewModel<AuthState, AuthSideEffect>() {
    override fun createInitialState(): AuthState = AuthState()

    fun loginWithKakao() {
        reduce { state.copy(isLoading = true) }

        viewModelScope.launch {
            repository
                .login(DeviceAuthToken(""))
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
