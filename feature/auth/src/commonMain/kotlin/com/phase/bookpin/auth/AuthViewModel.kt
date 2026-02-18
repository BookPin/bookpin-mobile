package com.phase.bookpin.auth

import androidx.lifecycle.viewModelScope
import bookpin.auth.generated.resources.Res
import bookpin.auth.generated.resources.retry
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.auth.AuthRepository
import com.phase.bookpin.domain.device.GetOrCreateDeviceIdUseCase
import com.phase.bookpin.model.auth.DeviceAuthToken
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
    private val getDeviceId: GetOrCreateDeviceIdUseCase,
) : BaseViewModel<AuthState, AuthSideEffect>() {
    override fun createInitialState(): AuthState = AuthState()

    fun login() {
        reduce { state.copy(isLoading = true) }

        viewModelScope.launch {
            val deviceId = getDeviceId()
            repository
                .login(DeviceAuthToken(deviceId))
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
