package com.phase.bookpin

import androidx.compose.ui.window.ComposeUIViewController

fun getBookPinViewController() = ComposeUIViewController {
    KoinHelper.initKoin()
    BookPinApp()
}
