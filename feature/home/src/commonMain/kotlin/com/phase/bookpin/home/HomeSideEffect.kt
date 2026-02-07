package com.phase.bookpin.home

sealed interface HomeSideEffect {
    data class ShowSnackbar(
        val message: String,
    ) : HomeSideEffect
    data object NavigateToSettings : HomeSideEffect
    data class NavigateToBookDetail(
        val bookId: String,
    ) : HomeSideEffect
    data object NavigateToAddBook : HomeSideEffect
}
