package com.phase.bookpin.data.auth.com.phase.bookpin.data.auth

import com.phase.bookpin.data.auth.kakao.AndroidKakaoAuth
import com.phase.bookpin.domain.kakao.KakaoAuth
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val dataAuthModule: Module = module {
    singleOf(::AndroidKakaoAuth) { bind<KakaoAuth>() }
}
