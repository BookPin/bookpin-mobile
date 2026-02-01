package com.phase.bookpin.androidapp

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.phase.bookpin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BookPinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookPinApplication)
            modules(appModule)
        }
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}
