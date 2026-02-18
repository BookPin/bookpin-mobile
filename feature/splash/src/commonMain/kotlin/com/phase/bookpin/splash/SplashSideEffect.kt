package com.phase.bookpin.splash

import org.jetbrains.compose.resources.StringResource

sealed interface SplashSideEffect {
    data object NavigateToHome : SplashSideEffect
    data class ShowSnackbar(
        val message: StringResource,
    ) : SplashSideEffect
}
