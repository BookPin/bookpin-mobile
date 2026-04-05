package com.phase.bookpin.bookmark.di

import com.phase.bookpin.bookmark.add.AddBookmarkViewModel
import com.phase.bookpin.bookmark.detail.BookDetailViewModel
import com.phase.bookpin.bookmark.detail.BookmarkDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val bookDetailModule = module {
    includes(bookmarkPlatformModule)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::AddBookmarkViewModel)
    viewModelOf(::BookmarkDetailViewModel)
}
