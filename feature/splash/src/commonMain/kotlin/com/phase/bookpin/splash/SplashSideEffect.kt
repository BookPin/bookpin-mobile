package com.phase.bookpin.splash

sealed interface SplashSideEffect {
    data object NavigateToHome : SplashSideEffect
    data class ShowSnackbar(
        val message: String,
    ) : SplashSideEffect
}
