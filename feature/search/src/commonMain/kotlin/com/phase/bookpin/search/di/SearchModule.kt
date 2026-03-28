package com.phase.bookpin.search.di

import com.phase.bookpin.search.SearchViewModel
import com.phase.bookpin.search.preview.BookPreviewViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::BookPreviewViewModel)
}
