package com.phase.bookpin.settings

import com.phase.bookpin.model.book.LatestBookmark

data class SettingsState(
    val profileName: String = "독서하는 사람",
    val accountType: String = "카카오 계정",
    val showLogoutDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
    val latestBookmark: LatestBookmark? = null,
)
