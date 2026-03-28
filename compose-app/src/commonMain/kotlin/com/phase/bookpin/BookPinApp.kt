package com.phase.bookpin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.navigation.BookDetailRoute
import com.phase.bookpin.navigation.BookPinNavHost
import com.phase.bookpin.navigation.HomeRoute
import com.phase.bookpin.navigation.SearchRoute
import com.phase.bookpin.navigation.SettingsRoute
import com.phase.bookpin.navigation.SplashRoute
import com.phase.bookpin.navigation.navSerializersModule
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

        val snackbarHost = remember(viewModel) {
            object : BookPinSnackbarHost {
                override fun showSnackbar(message: String) {
                    viewModel.handleShowSnackbarEvent(message)
                }
            }
        }

        val backStack = rememberNavBackStack(
            SavedStateConfiguration {
                serializersModule = navSerializersModule
            },
            SplashRoute,
        )

        viewModel.sideEffect.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is RootSideEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
                }

                is RootSideEffect.NavigateToSplash -> {
                    backStack.clear()
                    backStack.add(SplashRoute)
                }
            }
        }

        CompositionLocalProvider(
            LocalSnackbarHost provides snackbarHost,
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                modifier = Modifier.fillMaxSize(),
                containerColor = BookPinTheme.colors.bgCanvas,
            ) {
                BookPinNavHost(
                    backStack = backStack,
                    onNavigateToHome = {
                        backStack.clear()
                        backStack.add(HomeRoute)
                    },
                    onNavigateToSearch = {
                        backStack.add(SearchRoute)
                    },
                    onNavigateToBookDetail = { bookId ->
                        backStack.add(BookDetailRoute(bookId))
                    },
                    onNavigateToBookPreview = { route ->
                        backStack.add(route)
                    },
                    onNavigateToSettings = {
                        backStack.add(SettingsRoute)
                    },
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    },
                    onLogout = {
                        backStack.clear()
                        backStack.add(SplashRoute)
                    },
                )
            }
        }
    }
}
