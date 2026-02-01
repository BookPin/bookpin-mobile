package com.phase.bookpin.di

import co.touchlab.kermit.Logger
import com.phase.bookpin.auth.di.authModule
import com.phase.bookpin.data.api.navigation.Navigator
import com.phase.bookpin.data.auth.com.phase.bookpin.data.auth.dataAuthModule
import com.phase.bookpin.data.di.dataModule
import com.phase.bookpin.data.local.di.dataLocalModule
import com.phase.bookpin.data.remote.di.dataRemoteModule
import com.phase.bookpin.navigation.NavigatorImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    includes(
        authModule,
        dataModule,
        dataAuthModule,
        dataLocalModule,
        dataRemoteModule,
    )

    singleOf(::NavigatorImpl) { bind<Navigator>() }
    single { Logger.withTag("BookPin") }
}
