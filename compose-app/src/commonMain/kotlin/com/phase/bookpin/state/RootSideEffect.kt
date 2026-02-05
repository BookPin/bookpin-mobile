package com.phase.bookpin.state

sealed interface RootSideEffect {
    data class ShowSnackbar(
        val message: String,
    ) : RootSideEffect

    data object NavigateToAuth : RootSideEffect
}
