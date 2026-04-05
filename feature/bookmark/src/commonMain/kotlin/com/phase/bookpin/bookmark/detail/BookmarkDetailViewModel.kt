package com.phase.bookpin.bookmark.detail

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.book.BookRepository
import kotlinx.coroutines.launch

class BookmarkDetailViewModel(
    private val bookRepository: BookRepository,
) : BaseViewModel<BookmarkDetailState, BookmarkDetailSideEffect>() {
    override fun createInitialState(): BookmarkDetailState = BookmarkDetailState()

    fun onDeleteClick() {
        reduce { copy(showDeleteDialog = true) }
    }

    fun onDeleteDismiss() {
        reduce { copy(showDeleteDialog = false) }
    }

    fun onDeleteConfirm(bookId: Long, bookmarkId: Long) {
        reduce { copy(showDeleteDialog = false) }
        if (uiState.value.isLoading) return
        viewModelScope.launch {
            reduce { copy(isLoading = true) }
            bookRepository
                .deleteBookmark(bookId, bookmarkId)
                .onSuccess {
                    reduce { copy(isLoading = false) }
                    postSideEffect(BookmarkDetailSideEffect.NavigateBack)
                }.onFailure { error ->
                    reduce { copy(isLoading = false) }
                    postSideEffect(
                        BookmarkDetailSideEffect.ShowSnackbar(
                            error.message ?: "오류가 발생했습니다.",
                        ),
                    )
                }
        }
    }
}
