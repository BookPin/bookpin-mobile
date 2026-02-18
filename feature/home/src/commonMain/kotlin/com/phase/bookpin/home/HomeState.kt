package com.phase.bookpin.home

import com.phase.bookpin.model.book.BookItem

data class HomeState(
    val books: List<BookItem> = emptyList(),
)
