package com.phase.bookpin.data.remote.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp

actual fun createPlatformHttpClient(
    block: HttpClientConfig<*>.() -> Unit,
): HttpClient = HttpClient(OkHttp, block)
