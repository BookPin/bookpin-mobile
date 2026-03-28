package com.phase.bookpin.splash

import androidx.lifecycle.viewModelScope
import bookpin.splash.generated.resources.Res
import bookpin.splash.generated.resources.retry
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.auth.AuthRepository
import com.phase.bookpin.domain.device.DeviceRepository
import com.phase.bookpin.model.auth.DeviceAuthToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SplashViewModel(
    private val deviceRepository: DeviceRepository,
    private val authRepository: AuthRepository,
) : BaseViewModel<SplashState, SplashSideEffect>() {
    override fun createInitialState(): SplashState = SplashState

    init {
        startSplash()
    }

    private fun startSplash() {
        viewModelScope.launch {
            val start = Clock.System.now()

            val deviceIdResult = deviceRepository.getDeviceId()
            if (deviceIdResult.isFailure) {
                val loginResult = login()
                if (loginResult.isFailure) {
                    postSideEffect(
                        SplashSideEffect.ShowSnackbar((Res.string.retry)),
                    )
                    return@launch
                }
            }

            val elapsed = Clock.System.now() - start
            val remaining = (MIN_SPLASH_DURATION.milliseconds - elapsed)
            if (remaining.isPositive()) {
                delay(remaining)
            }

            postSideEffect(SplashSideEffect.NavigateToHome)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun login(): Result<Unit> {
        val newDeviceId = Uuid.random().toString()
        val loginResult = authRepository.login(DeviceAuthToken(newDeviceId))

        if (loginResult.isSuccess) {
            deviceRepository.saveDeviceId(newDeviceId)
        }

        return loginResult
    }

    companion object {
        const val MIN_SPLASH_DURATION = 1000
    }
}
