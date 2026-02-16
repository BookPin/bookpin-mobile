package com.phase.bookpin.auth

import org.jetbrains.compose.resources.StringResource

sealed interface AuthSideEffect {
    data object NavigateToHome : AuthSideEffect
    data class ShowSnackbar(
        val message: StringResource,
    ) : AuthSideEffect
}
