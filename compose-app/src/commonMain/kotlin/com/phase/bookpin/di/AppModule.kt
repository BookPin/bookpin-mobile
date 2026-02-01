package com.phase.bookpin.di

import co.touchlab.kermit.Logger
import com.phase.bookpin.data.api.navigation.Navigator
import com.phase.bookpin.navigation.NavigatorImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::NavigatorImpl) { bind<Navigator>() }
    single { Logger.withTag("BookPin") }
}
