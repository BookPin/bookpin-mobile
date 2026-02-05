package com.phase.bookpin.di

import co.touchlab.kermit.Logger
import com.phase.bookpin.auth.di.authModule
import com.phase.bookpin.data.auth.com.phase.bookpin.data.auth.dataAuthModule
import com.phase.bookpin.data.di.dataModule
import com.phase.bookpin.data.local.di.dataLocalModule
import com.phase.bookpin.data.remote.di.dataRemoteModule
import com.phase.bookpin.home.di.homeModule
import com.phase.bookpin.state.RootViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(
        authModule,
        homeModule,
        dataModule,
        dataAuthModule,
        dataLocalModule,
        dataRemoteModule,
    )

    viewModelOf(::RootViewModel)
    single { Logger.withTag("BookPin") }
}
