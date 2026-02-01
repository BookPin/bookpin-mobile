package com.phase.bookpin.data.di

import com.phase.bookpin.data.auth.AuthRepositoryImpl
import com.phase.bookpin.domain.auth.AuthRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
}
