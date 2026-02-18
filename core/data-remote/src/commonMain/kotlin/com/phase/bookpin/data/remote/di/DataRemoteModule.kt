package com.phase.bookpin.data.remote.di

import com.phase.bookpin.data.api.auth.AuthRemoteDataSource
import com.phase.bookpin.data.api.auth.SessionDataSource
import com.phase.bookpin.data.remote.auth.AuthRemoteDataSourceImpl
import com.phase.bookpin.data.remote.client.createHttpClient
import com.phase.bookpin.data.remote.client.createRefreshHttpClient
import com.phase.bookpin.data.remote.auth.SessionDataSourceImpl
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val RefreshHttpClient = named("RefreshHttpClient")

val dataRemoteModule = module {
    singleOf(::SessionDataSourceImpl) { bind<SessionDataSource>() }

    single(RefreshHttpClient) { createRefreshHttpClient(logger = get()) }
    single<HttpClient> {
        createHttpClient(
            refreshClient = get(RefreshHttpClient),
            local = get(),
            sessionDataSource = get(),
            logger = get(),
        )
    }

    single<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(
            client = get(),
        )
    }
}
