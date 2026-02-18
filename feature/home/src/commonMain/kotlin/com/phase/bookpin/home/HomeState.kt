package com.phase.bookpin.home

import com.phase.bookpin.model.book.BookItem

data class HomeState(
    val isLoading: Boolean = false,
    val bookItems: List<BookItem> = emptyList(),
)
