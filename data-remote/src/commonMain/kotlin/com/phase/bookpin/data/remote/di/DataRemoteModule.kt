package com.phase.bookpin.data.remote.di

import com.phase.bookpin.data.api.auth.AuthRemoteDataSource
import com.phase.bookpin.data.remote.auth.AuthRemoteDataSourceImpl
import com.phase.bookpin.data.remote.client.createHttpClient
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataRemoteModule = module {
    single<HttpClient> {
        createHttpClient(
            local = get(),
            navigator = get(),
            logger = get(),
        )
    }
    singleOf(::AuthRemoteDataSourceImpl) { bind<AuthRemoteDataSource>() }
}
