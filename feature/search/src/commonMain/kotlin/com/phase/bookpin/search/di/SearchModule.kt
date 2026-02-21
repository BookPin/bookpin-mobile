package com.phase.bookpin.search.di

import com.phase.bookpin.search.SearchViewModel
import com.phase.bookpin.search.addbook.AddBookViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::AddBookViewModel)
}
