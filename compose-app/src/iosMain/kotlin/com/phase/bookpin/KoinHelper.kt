package com.phase.bookpin

import com.phase.bookpin.di.appModule
import org.koin.core.context.startKoin

object KoinHelper {
    internal fun initKoin() {
        startKoin {
            modules(appModule)
        }
    }
}
