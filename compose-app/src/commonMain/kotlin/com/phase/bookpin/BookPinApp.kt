package com.phase.bookpin

import androidx.compose.runtime.Composable
import com.phase.bookpin.auth.AuthScreen
import com.phase.bookpin.designsystem.BookPinTheme

@Composable
fun BookPinApp() {
    BookPinTheme {
        AuthScreen()
    }
}
