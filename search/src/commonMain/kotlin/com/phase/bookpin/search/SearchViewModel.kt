package com.phase.bookpin.search

import com.phase.bookpin.common.BaseViewModel

class SearchViewModel : BaseViewModel<SearchState, SearchSideEffect>() {
    override fun createInitialState(): SearchState = SearchState()

    fun onQueryChange(query: String) {
        reduce { copy(query = query) }
    }

    fun onSearch() {
        if (state.query.isBlank()) return

        reduce { copy(isLoading = true, hasSearched = true) }

        // TODO: Implement actual search logic with repository
        // For now, simulate no results
        reduce {
            copy(
                isLoading = false,
                searchResults = emptyList(),
            )
        }
    }

    fun onCloseClick() {
        postSideEffect(SearchSideEffect.NavigateBack)
    }

    fun onManualInputClick() {
        postSideEffect(SearchSideEffect.NavigateToManualInput)
    }

    fun onBookClick(bookId: String) {
        postSideEffect(SearchSideEffect.NavigateToBookDetail(bookId))
    }
}
