package com.phase.bookpin.data.remote.user

import com.phase.bookpin.data.api.user.UserRemoteDataSource
import com.phase.bookpin.data.api.user.UserResponse
import com.phase.bookpin.data.remote.client.safeRequest
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.path

class UserRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : UserRemoteDataSource {
    override suspend fun getUser(): Result<UserResponse> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1/users/me")
            }
        }
}
