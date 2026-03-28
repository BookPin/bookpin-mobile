package com.phase.bookpin.search.preview

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.search.SearchRepository
import kotlinx.coroutines.launch

class BookPreviewViewModel(
    private val searchRepository: SearchRepository,
) : BaseViewModel<BookPreviewState, BookPreviewSideEffect>() {
    override fun createInitialState(): BookPreviewState = BookPreviewState()

    fun initWithBookInfo(
        title: String,
        author: String,
        totalPage: Int,
        imageUrl: String,
        isbn: String,
    ) {
        reduce {
            copy(
                title = title,
                author = author,
                totalPage = totalPage,
                imageUrl = imageUrl,
                isbn = isbn,
            )
        }
    }

    fun onTitleChange(title: String) {
        reduce { copy(title = title) }
    }

    fun onAuthorChange(author: String) {
        reduce { copy(author = author) }
    }

    fun onTotalPageChange(totalPage: String) {
        val page = totalPage.toIntOrNull() ?: 0
        reduce { copy(totalPage = page) }
    }

    fun onBackClick() {
        postSideEffect(BookPreviewSideEffect.NavigateBack)
    }

    fun onCloseClick() {
        postSideEffect(BookPreviewSideEffect.NavigateClose)
    }

    fun onSubmit() {
        val state = uiState.value
        viewModelScope.launch {
            reduce { copy(isLoading = true) }
            searchRepository
                .addBook(
                    title = state.title,
                    author = state.author,
                    imageUrl = state.imageUrl,
                    totalPage = state.totalPage,
                ).onSuccess {
                    postSideEffect(BookPreviewSideEffect.NavigateToHome)
                }.onFailure { error ->
                    reduce { copy(isLoading = false) }
                    postSideEffect(
                        BookPreviewSideEffect.ShowSnackbar(
                            error.message ?: "책 추가에 실패했습니다.",
                        ),
                    )
                }
        }
    }
}
