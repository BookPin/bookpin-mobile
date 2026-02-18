package com.phase.bookpin.data.remote.auth

import com.phase.bookpin.data.api.auth.AuthRemoteDataSource
import com.phase.bookpin.data.api.auth.model.DeviceAuthTokenRequest
import com.phase.bookpin.data.api.auth.model.LoginResponse
import com.phase.bookpin.data.remote.client.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class AuthRemoteDataSourceImpl(
    private val client: HttpClient,
) : AuthRemoteDataSource {
    override suspend fun login(request: DeviceAuthTokenRequest): Result<LoginResponse> =
        client.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1/auth/login/device")
            }
            setBody(request)
        }
}
