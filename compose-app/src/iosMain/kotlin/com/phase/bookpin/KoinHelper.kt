package com.phase.bookpin

import com.phase.bookpin.auth.di.authModule
import com.phase.bookpin.data.di.dataModule
import com.phase.bookpin.data.local.di.dataLocalModule
import com.phase.bookpin.data.remote.di.dataRemoteModule
import com.phase.bookpin.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule, dataLocalModule, dataRemoteModule, dataModule, authModule)
    }
}
