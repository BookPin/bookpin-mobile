package com.phase.bookpin.settings

import com.phase.bookpin.common.BaseViewModel

class SettingsViewModel : BaseViewModel<SettingsState, SettingsSideEffect>() {
    override fun createInitialState(): SettingsState = SettingsState()

    fun onBackClick() {
        postSideEffect(SettingsSideEffect.NavigateBack)
    }

    fun onContactClick() {
        postSideEffect(SettingsSideEffect.ShowSnackbar("문의하기 기능은 준비 중입니다."))
    }

    fun onLogoutClick() {
        postSideEffect(SettingsSideEffect.Logout)
    }

    fun onDeleteAccountClick() {
        postSideEffect(SettingsSideEffect.ShowSnackbar("회원 탈퇴 기능은 준비 중입니다."))
    }
}
