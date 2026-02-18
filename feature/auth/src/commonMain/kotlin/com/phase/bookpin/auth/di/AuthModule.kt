package com.phase.bookpin.auth.di

import com.phase.bookpin.auth.AuthViewModel
import com.phase.bookpin.domain.device.GetOrCreateDeviceIdUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    factoryOf(::GetOrCreateDeviceIdUseCase)
    viewModelOf(::AuthViewModel)
}
