package com.phase.bookpin.home

import org.jetbrains.compose.resources.StringResource

sealed interface HomeSideEffect {
    data class ShowSnackbar(
        val message: StringResource,
    ) : HomeSideEffect
    data object NavigateToSettings : HomeSideEffect
    data class NavigateToBookDetail(
        val bookId: Long,
    ) : HomeSideEffect
    data object NavigateToAddBook : HomeSideEffect
    data class NavigateToAddBookmark(
        val bookId: Long,
    ) : HomeSideEffect
}
