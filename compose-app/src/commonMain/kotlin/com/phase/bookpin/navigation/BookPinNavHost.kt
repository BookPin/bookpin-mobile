package com.phase.bookpin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.phase.bookpin.auth.AuthScreen
import com.phase.bookpin.home.HomeScreen

@Composable
fun BookPinNavHost(
    backStack: NavBackStack<NavKey>,
    onNavigateToHome: () -> Unit,
) {
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<AuthRoute> {
                AuthScreen(
                    onNavigateToHome = onNavigateToHome,
                )
            }

            entry<HomeRoute> {
                HomeScreen()
            }
        },
    )
}
