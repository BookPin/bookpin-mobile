package com.phase.bookpin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.phase.bookpin.auth.AuthScreen
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.state.RootSideEffect
import com.phase.bookpin.state.RootViewModel
import org.koin.compose.viewmodel.koinViewModel
import com.phase.bookpin.common.snackbar.SnackbarHost as BookPinSnackbarHost

@Composable
fun BookPinApp(
    viewModel: RootViewModel = koinViewModel(),
) {
    BookPinTheme {
        val snackbarHostState = remember { SnackbarHostState() }

        val snackbarHost = object : BookPinSnackbarHost {
            override fun showSnackbar(message: String) {
                viewModel.handleShowSnackbarEvent(message)
            }
        }

        viewModel.sideEffect.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is RootSideEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
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
