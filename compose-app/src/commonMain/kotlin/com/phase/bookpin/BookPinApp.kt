package com.phase.bookpin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.phase.bookpin.auth.AuthScreen
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import kotlinx.coroutines.launch
import com.phase.bookpin.common.snackbar.SnackbarHost as BookPinSnackbarHost

@Composable
fun BookPinApp() {
    BookPinTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        val snackbarHost = object : BookPinSnackbarHost {
            override fun showSnackbar(message: String) {
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        }

        CompositionLocalProvider(
            LocalSnackbarHost provides snackbarHost,
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                modifier = Modifier.fillMaxSize(),
            ) {
                AuthScreen()
            }
        }
    }
}
