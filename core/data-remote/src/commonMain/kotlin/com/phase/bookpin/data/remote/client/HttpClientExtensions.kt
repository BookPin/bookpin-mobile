package com.phase.bookpin.data.remote.client

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.http.isSuccess
import io.ktor.util.AttributeKey
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): Result<T> =
    runCatching {
        val response = request {
            block()
        }
        return if (response.status.isSuccess()) {
            val parsedBody = response.call.attributes.getOrNull(AttributeKey("parsedBody"))
            val responseBody = response.body<T>()
            Result.success(parsedBody as? T ?: responseBody)
        } else {
            val errorBody = response.body<RemoteException>()
            Result.failure(errorBody)
        }
    }.getOrElse { exception ->
        Logger.e(exception.message.toString())
        when (exception) {
            is SerializationException -> {
                Result.failure(RemoteException().toSerializationException())
            }

            is UnresolvedAddressException -> {
                Result.failure(RemoteException().toUnresolvedAddressException())
            }

            else -> {
                Result.failure(RemoteException())
            }
        }
    }
