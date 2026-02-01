package com.phase.bookpin.androidapp

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.phase.bookpin.auth.di.authModule
import com.phase.bookpin.data.di.dataModule
import com.phase.bookpin.data.local.datastore.initDataStore
import com.phase.bookpin.data.local.di.dataLocalModule
import com.phase.bookpin.data.remote.di.dataRemoteModule
import com.phase.bookpin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BookPinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDataStore(this)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        startKoin {
            androidContext(this@BookPinApplication)
            modules(appModule, dataLocalModule, dataRemoteModule, dataModule, authModule)
        }
    }
}
