package com.phase.bookpin.data.remote.client

import com.phase.bookpin.data.api.auth.RefreshTokenRequest
import com.phase.bookpin.data.api.auth.RefreshTokenResponse
import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.data.api.datastore.DataStoreKey
import com.phase.bookpin.data.api.session.SessionEventDataSource
import com.phase.bookpin.data.remote.BuildKonfig
import com.phase.bookpin.data.remote.BuildKonfig.AMAZON_S3
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlin.time.Clock
import kotlin.time.Instant
import co.touchlab.kermit.Logger as KermitLogger

private const val REQUEST_TIMEOUT_MILLIS = 50_000L

expect fun createPlatformHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient

internal fun createHttpClient(
    refreshClient: HttpClient,
    local: BookPinPreferenceDataStore,
    sessionEventDataSource: SessionEventDataSource,
    logger: KermitLogger,
): HttpClient = createPlatformHttpClient {

    defaultRequest {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        url(BuildKonfig.BASE_URL)
    }

    install(Auth) {
        bearer {
            loadTokens {
                loadBearerTokens(local)
            }

            refreshTokens {
                refreshBearerTokens(refreshClient, local, sessionEventDataSource)
            }

            sendWithoutRequest {
                !it.url.host.endsWith(AMAZON_S3)
            }
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
    }

    install(ContentNegotiation) {
        json(clientJson)
    }

    install(Logging) {
        level = LogLevel.ALL
        this.logger = object : Logger {
            override fun log(message: String) {
                if (BuildKonfig.IS_DEBUG) {
                    logger.i { message }
                }
            }
        }
    }
}

private suspend fun loadBearerTokens(
    local: BookPinPreferenceDataStore,
): BearerTokens {
    return BearerTokens(
        accessToken = local.getString(DataStoreKey.ACCESS_TOKEN).first(),
        refreshToken = local.getString(DataStoreKey.REFRESH_TOKEN).first(),
    )
}

private suspend fun refreshBearerTokens(
    refreshClient: HttpClient,
    local: BookPinPreferenceDataStore,
    sessionEventDataSource: SessionEventDataSource,
): BearerTokens? {
    if (isRefreshTokenExpired(local)) {
        sessionEventDataSource.emitSessionExpired()
        return null
    }

    val refreshToken = local.getString(DataStoreKey.REFRESH_TOKEN).first()
    if (refreshToken.isBlank()) {
        sessionEventDataSource.emitSessionExpired()
        return null
    }

    return runCatching {
        refreshClient.post {
            url("api/v1/auth/refresh")
            setBody(RefreshTokenRequest(refreshToken))
        }.body<RefreshTokenResponse>()
    }.mapCatching { response ->
        local.saveString(DataStoreKey.ACCESS_TOKEN, response.accessToken)
        local.saveString(DataStoreKey.REFRESH_TOKEN, response.refreshToken)
        BearerTokens(response.accessToken, response.refreshToken)
    }.onFailure { throwable ->
        if (throwable.shouldLogoutOnRefreshFailure()) {
            sessionEventDataSource.emitInvalidRefreshToken()
        }
    }.getOrNull()
}

internal fun createRefreshHttpClient(logger: KermitLogger): HttpClient = createPlatformHttpClient {
    defaultRequest {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        url(BuildKonfig.BASE_URL)
    }

    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
    }

    install(ContentNegotiation) {
        json(clientJson)
    }

    install(Logging) {
        level = LogLevel.ALL
        this.logger = object : Logger {
            override fun log(message: String) {
                if (BuildKonfig.IS_DEBUG) {
                    logger.i { message }
                }
            }
        }
    }
}

private suspend fun isRefreshTokenExpired(local: BookPinPreferenceDataStore): Boolean {
    val expiredAt = local
        .getString(DataStoreKey.REFRESH_TOKEN_EXPIRED_AT)
        .first()

    if (expiredAt.isEmpty()) {
        return true
    }

    return runCatching {
        Instant.parse(expiredAt) <= Clock.System.now()
    }.getOrDefault(true)
}
