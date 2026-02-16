package com.phase.bookpin.bookdetail.di

import com.phase.bookpin.bookdetail.BookDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val bookDetailModule = module {
    viewModelOf(::BookDetailViewModel)
}
