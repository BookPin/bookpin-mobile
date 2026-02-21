package com.phase.bookpin.search.addbook

sealed interface AddBookSideEffect {
    data object NavigateBack : AddBookSideEffect
    data object NavigateClose : AddBookSideEffect
    data object NavigateToHome : AddBookSideEffect
    data class ShowSnackbar(
        val message: String,
    ) : AddBookSideEffect
}
