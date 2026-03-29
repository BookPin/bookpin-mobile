package com.phase.bookpin.settings

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.book.BookRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val bookRepository: BookRepository,
) : BaseViewModel<SettingsState, SettingsSideEffect>() {
    override fun createInitialState(): SettingsState = SettingsState()

    init {
        loadLatestBookmark()
    }

    private fun loadLatestBookmark() {
        viewModelScope.launch {
            bookRepository
                .getLatestBookmark()
                .onSuccess { bookmark ->
                    bookmark?.let {
                        reduce { copy(latestBookmark = it) }
                    }
                }
        }
    }

    fun onBackClick() {
        postSideEffect(SettingsSideEffect.NavigateBack)
    }

    fun onContactClick() {
        postSideEffect(SettingsSideEffect.ShowSnackbar("문의하기 기능은 준비 중입니다."))
    }

    fun onLogoutClick() {
        reduce { copy(showLogoutDialog = true) }
    }

    fun onLogoutDismiss() {
        reduce { copy(showLogoutDialog = false) }
    }

    fun onLogoutConfirm() {
        reduce { copy(showLogoutDialog = false) }
        postSideEffect(SettingsSideEffect.Logout)
    }

    fun onDeleteAccountClick() {
        reduce { copy(showDeleteAccountDialog = true) }
    }

    fun onDeleteAccountDismiss() {
        reduce { copy(showDeleteAccountDialog = false) }
    }

    fun onDeleteAccountConfirm() {
        reduce { copy(showDeleteAccountDialog = false) }
        postSideEffect(SettingsSideEffect.DeleteAccount)
    }
}
