package com.phase.bookpin.home

import androidx.lifecycle.viewModelScope
import bookpin.home.generated.resources.Res
import bookpin.home.generated.resources.unknown_error_message
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.book.BookRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val bookRepository: BookRepository,
) : BaseViewModel<HomeState, HomeSideEffect>() {
    override fun createInitialState(): HomeState = HomeState()

    fun onEnterScreen() {
        reduce {
            state.copy(isLoading = true)
        }

        viewModelScope.launch {
            bookRepository
                .getBookItems()
                .onSuccess { items ->
                    reduce {
                        state.copy(bookItems = items)
                    }
                }.onFailure {
                    postSideEffect(
                        HomeSideEffect.ShowSnackbar(Res.string.unknown_error_message),
                    )
                }.also {
                    reduce { state.copy(isLoading = false) }
                }
        }
    }

    fun onSettingsClick() {
        postSideEffect(HomeSideEffect.NavigateToSettings)
    }

    fun onAddBookClick() {
        postSideEffect(HomeSideEffect.NavigateToAddBook)
    }

    fun onBookClick(bookId: Long) {
        postSideEffect(HomeSideEffect.NavigateToBookDetail(bookId))
    }

    fun onAddBookmarkClick(bookId: Long) {
        postSideEffect(HomeSideEffect.NavigateToAddBookmark(bookId))
    }
}
