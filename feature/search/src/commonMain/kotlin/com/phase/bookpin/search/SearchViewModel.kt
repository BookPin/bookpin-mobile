package com.phase.bookpin.search

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.search.SearchRepository
import com.phase.bookpin.model.search.BookSearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository,
) : BaseViewModel<SearchState, SearchSideEffect>() {
    private val queryInput: MutableStateFlow<String> = MutableStateFlow("")

    override fun createInitialState(): SearchState = SearchState()

    init {
        observeQueryInput()
    }

    fun onQueryChange(query: String) {
        queryInput.value = query
        reduce { copy(query = query) }
    }

    private fun observeQueryInput() {
        viewModelScope.launch {
            queryInput
                .debounce(INPUT_DEBOUNCE_TIME)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotBlank()) {
                        searchBooks(query)
                    } else {
                        reduce { copy(searchResults = emptyList(), hasSearched = false) }
                    }
                }
        }
    }

    private suspend fun searchBooks(query: String) {
        reduce { copy(isLoading = true, hasSearched = true) }

        repository
            .searchBooks(query)
            .onSuccess { results ->
                reduce { copy(isLoading = false, searchResults = results) }
            }.onFailure { error ->
                reduce { copy(isLoading = false, searchResults = emptyList()) }
                postSideEffect(
                    SearchSideEffect.ShowSnackbar(
                        error.message ?: "검색 중 오류가 발생했습니다.",
                    ),
                )
            }
    }

    fun onCloseClick() {
        postSideEffect(SearchSideEffect.NavigateBack)
    }

    fun onManualInputClick() {
        postSideEffect(SearchSideEffect.NavigateToManualInput)
    }

    fun onBookClick(result: BookSearchResult) {
        postSideEffect(SearchSideEffect.NavigateToAddBook(result))
    }

    companion object {
        private const val INPUT_DEBOUNCE_TIME = 500L
    }
}
