package com.phase.bookpin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.phase.bookpin.auth.AuthScreen
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.domain.session.SessionRepository
import com.phase.bookpin.model.SessionEvent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import com.phase.bookpin.common.snackbar.SnackbarHost as BookPinSnackbarHost

@Composable
fun BookPinApp() {
    val sessionRepository: SessionRepository = koinInject()

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

        LaunchedEffect(Unit) {
            sessionRepository.events.collect { event ->
                when (event) {
                    SessionEvent.SessionExpired,
                    SessionEvent.InvalidRefreshToken,
                    -> {
                        sessionRepository.clearSession()
                        snackbarHostState.showSnackbar("세션이 만료되었습니다. 다시 로그인해주세요.")
                        // TODO: Navigation 구현 후 AuthScreen으로 이동
                    }
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
