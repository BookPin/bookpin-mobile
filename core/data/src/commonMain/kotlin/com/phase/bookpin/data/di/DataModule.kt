package com.phase.bookpin.data.di

import com.phase.bookpin.data.api.auth.SessionEventListener
import com.phase.bookpin.data.auth.AuthRepositoryImpl
import com.phase.bookpin.data.auth.SessionRepositoryImpl
import com.phase.bookpin.data.book.BookRepositoryImpl
import com.phase.bookpin.data.device.DeviceRepositoryImpl
import com.phase.bookpin.data.image.ImageRepositoryImpl
import com.phase.bookpin.data.search.SearchRepositoryImpl
import com.phase.bookpin.data.user.UserRepositoryImpl
import com.phase.bookpin.domain.auth.AuthRepository
import com.phase.bookpin.domain.auth.SessionRepository
import com.phase.bookpin.domain.book.BookRepository
import com.phase.bookpin.domain.device.DeviceRepository
import com.phase.bookpin.domain.image.ImageRepository
import com.phase.bookpin.domain.search.SearchRepository
import com.phase.bookpin.domain.user.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::SessionRepositoryImpl) {
        bind<SessionRepository>()
        bind<SessionEventListener>()
    }
    singleOf(::DeviceRepositoryImpl) { bind<DeviceRepository>() }
    singleOf(::BookRepositoryImpl) { bind<BookRepository>() }
    singleOf(::ImageRepositoryImpl) { bind<ImageRepository>() }
    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}
