package com.phase.bookpin.data.remote.auth

import com.phase.bookpin.data.api.auth.AuthRemoteDataSource
import com.phase.bookpin.data.api.auth.LoginResponse
import com.phase.bookpin.data.api.auth.SocialAuthTokenRequest
import com.phase.bookpin.data.remote.client.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class AuthRemoteDataSourceImpl(
    private val client: HttpClient,
) : AuthRemoteDataSource {
    override suspend fun login(request: SocialAuthTokenRequest): Result<LoginResponse> =
        client.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1/auth/login")
            }
            setBody(request)
        }
}
