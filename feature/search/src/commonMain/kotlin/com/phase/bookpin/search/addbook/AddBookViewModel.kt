package com.phase.bookpin.search.addbook

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.search.SearchRepository
import kotlinx.coroutines.launch

class AddBookViewModel(
    private val searchRepository: SearchRepository,
) : BaseViewModel<AddBookState, AddBookSideEffect>() {
    override fun createInitialState(): AddBookState = AddBookState()

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
        val filtered = totalPage.filter { it.isDigit() }
        reduce { copy(totalPage = filtered) }
    }

    fun onBackClick() {
        postSideEffect(AddBookSideEffect.NavigateBack)
    }

    fun onCloseClick() {
        postSideEffect(AddBookSideEffect.NavigateClose)
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
                    totalPage = state.totalPage.toInt(),
                ).onSuccess {
                    postSideEffect(AddBookSideEffect.NavigateToHome)
                }.onFailure { error ->
                    reduce { copy(isLoading = false) }
                    postSideEffect(
                        AddBookSideEffect.ShowSnackbar(
                            error.message ?: "책 추가에 실패했습니다.",
                        ),
                    )
                }
        }
    }
}
