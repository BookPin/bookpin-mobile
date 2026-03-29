package com.phase.bookpin.bookmark.di

import com.phase.bookpin.bookmark.add.ImageFileReader
import com.phase.bookpin.bookmark.add.TextRecognizer
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val bookmarkPlatformModule: Module = module {
    factory { TextRecognizer(androidContext()) }
    single { ImageFileReader(androidContext()) }
}
