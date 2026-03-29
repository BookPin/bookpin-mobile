package com.phase.bookpin.settings

import com.phase.bookpin.model.book.LatestBookmark

data class SettingsState(
    val profileName: String? = null,
    val profileImageUrl: String? = null,
    val accountType: String = "카카오 계정",
    val showLogoutDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
    val latestBookmark: LatestBookmark? = null,
)
