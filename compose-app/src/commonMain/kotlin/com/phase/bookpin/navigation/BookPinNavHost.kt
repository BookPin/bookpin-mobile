package com.phase.bookpin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.phase.bookpin.bookmark.BookDetailScreen
import com.phase.bookpin.home.HomeScreen
import com.phase.bookpin.search.SearchScreen
import com.phase.bookpin.search.preview.BookPreviewScreen
import com.phase.bookpin.settings.SettingsScreen
import com.phase.bookpin.splash.SplashScreen

@Composable
fun BookPinNavHost(
    backStack: NavBackStack<NavKey>,
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToBookDetail: (Long) -> Unit,
    onNavigateToBookPreview: (BookPreviewRoute) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit,
) {
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<SplashRoute> {
                SplashScreen(
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
                    onNavigateToManualInput = { onNavigateToBookPreview(BookPreviewRoute()) },
                    onNavigateToBookPreview = { result ->
                        onNavigateToBookPreview(
                            BookPreviewRoute(
                                title = result.title,
                                author = result.author,
                                totalPage = result.totalPage,
                                imageUrl = result.imageUrl,
                                isbn = result.isbn,
                            ),
                        )
                    },
                )
            }

            entry<BookPreviewRoute> { route ->
                BookPreviewScreen(
                    title = route.title,
                    author = route.author,
                    totalPage = route.totalPage,
                    imageUrl = route.imageUrl,
                    isbn = route.isbn,
                    onNavigateBack = onNavigateBack,
                    onNavigateClose = onNavigateBack,
                    onNavigateToHome = onNavigateToHome,
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
