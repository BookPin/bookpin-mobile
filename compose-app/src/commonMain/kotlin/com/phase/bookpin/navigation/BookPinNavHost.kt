package com.phase.bookpin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.phase.bookpin.auth.AuthScreen
import com.phase.bookpin.bookdetail.BookDetailScreen
import com.phase.bookpin.home.HomeScreen
import com.phase.bookpin.search.SearchScreen
import com.phase.bookpin.settings.SettingsScreen

@Composable
fun BookPinNavHost(
    backStack: NavBackStack<NavKey>,
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToBookDetail: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit,
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
                HomeScreen(
                    onNavigateToSearch = onNavigateToSearch,
                    onNavigateToBookDetail = onNavigateToBookDetail,
                    onNavigateToSettings = onNavigateToSettings,
                )
            }

            entry<SearchRoute> {
                SearchScreen(
                    onNavigateBack = onNavigateBack,
                )
            }

            entry<BookDetailRoute> { route ->
                BookDetailScreen(
                    bookId = route.bookId,
                    onNavigateBack = onNavigateBack,
                )
            }

            entry<SettingsRoute> {
                SettingsScreen(
                    onNavigateBack = onNavigateBack,
                    onLogout = onLogout,
                )
            }
        },
    )
}
