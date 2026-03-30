package com.phase.bookpin.bookmark.detail

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.book.BookRepository
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
) : BaseViewModel<BookDetailState, BookDetailSideEffect>() {
    override fun createInitialState(): BookDetailState = BookDetailState()

    fun init(bookId: Long) {
        loadBookDetail(bookId)
        loadTextBookmarks(bookId)
        loadPhotoBookmarks(bookId)
    }

    private fun loadBookDetail(bookId: Long) {
        if (uiState.value.isLoading) return
        viewModelScope.launch {
            reduce { copy(isLoading = true) }
            bookRepository
                .getBookDetail(bookId)
                .onSuccess { book ->
                    reduce { copy(book = book, isLoading = false) }
                }.onFailure { error ->
                    reduce { copy(isLoading = false) }
                    postSideEffect(BookDetailSideEffect.ShowSnackbar(error.message ?: "오류가 발생했습니다."))
                }
        }
    }

    private fun loadTextBookmarks(bookId: Long) {
        viewModelScope.launch {
            bookRepository
                .getTextBookmarks(bookId)
                .onSuccess { bookmarks ->
                    reduce { copy(textBookmarks = bookmarks) }
                }.onFailure { error ->
                    postSideEffect(BookDetailSideEffect.ShowSnackbar(error.message ?: "오류가 발생했습니다."))
                }
        }
    }

    private fun loadPhotoBookmarks(bookId: Long) {
        viewModelScope.launch {
            bookRepository
                .getPhotoBookmarks(bookId)
                .onSuccess { bookmarks ->
                    reduce { copy(photoBookmarks = bookmarks) }
                }.onFailure { error ->
                    postSideEffect(BookDetailSideEffect.ShowSnackbar(error.message ?: "오류가 발생했습니다."))
                }
        }
    }

    fun onBackClick() {
        postSideEffect(BookDetailSideEffect.NavigateBack)
    }

    fun onTabSelected(tab: BookmarkTab) {
        reduce { copy(selectedTab = tab) }
    }

    fun onMarkAsCompleteClick() {
        val bookId = uiState.value.book.id
        if (uiState.value.isLoading) return
        viewModelScope.launch {
            reduce { copy(isLoading = true) }
            bookRepository
                .completeBook(bookId)
                .onSuccess {
                    reduce { copy(isLoading = false, book = book.copy(isCompleted = true)) }
                    postSideEffect(BookDetailSideEffect.ShowSnackbar("완독으로 표시되었습니다"))
                    postSideEffect(BookDetailSideEffect.NavigateToHome)
                }.onFailure { error ->
                    reduce { copy(isLoading = false) }
                    postSideEffect(BookDetailSideEffect.ShowSnackbar(error.message ?: "오류가 발생했습니다."))
                }
        }
    }

    fun onAddBookmarkClick() {
        postSideEffect(BookDetailSideEffect.NavigateToAddBookmark)
    }
}
