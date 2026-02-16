package com.phase.bookpin.data.local.di

import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.data.local.datastore.BookPinPreferenceDataStoreImpl
import com.phase.bookpin.data.local.datastore.createDataStore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataLocalModule = module {
    single { createDataStore() }
    singleOf(::BookPinPreferenceDataStoreImpl) { bind<BookPinPreferenceDataStore>() }
}
