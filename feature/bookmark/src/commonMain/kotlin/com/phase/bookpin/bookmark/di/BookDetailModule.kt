package com.phase.bookpin.bookmark.di

import com.phase.bookpin.bookmark.detail.BookDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val bookDetailModule = module {
    viewModelOf(::BookDetailViewModel)
}
