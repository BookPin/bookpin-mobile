package com.phase.bookpin.data.di

import com.phase.bookpin.data.api.auth.SessionEventListener
import com.phase.bookpin.data.auth.AuthRepositoryImpl
import com.phase.bookpin.data.auth.SessionRepositoryImpl
import com.phase.bookpin.data.device.DeviceRepositoryImpl
import com.phase.bookpin.domain.auth.AuthRepository
import com.phase.bookpin.domain.auth.SessionRepository
import com.phase.bookpin.domain.device.DeviceRepository
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
}
