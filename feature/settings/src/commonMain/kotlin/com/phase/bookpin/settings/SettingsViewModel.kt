package com.phase.bookpin.settings

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.book.BookRepository
import com.phase.bookpin.domain.user.UserRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
) : BaseViewModel<SettingsState, SettingsSideEffect>() {
    override fun createInitialState(): SettingsState = SettingsState()

    init {
        loadUserProfile()
        loadLatestBookmark()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            userRepository
                .getUser()
                .onSuccess { user ->
                    reduce {
                        copy(
                            profileName = user.nickname,
                            profileImageUrl = user.profileImageUrl.ifEmpty { null },
                        )
                    }
                }
        }
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

    fun onViewAllBookmarksClick() {
        // TODO: Navigate to all bookmarks screen when available
        postSideEffect(SettingsSideEffect.NavigateToAllBookmarks)
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
