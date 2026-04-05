package com.phase.bookpin.settings

sealed interface SettingsSideEffect {
    data class ShowSnackbar(
        val message: String,
    ) : SettingsSideEffect
    data object NavigateBack : SettingsSideEffect
    data object OpenContact : SettingsSideEffect
    data object Logout : SettingsSideEffect
    data object DeleteAccount : SettingsSideEffect
}
