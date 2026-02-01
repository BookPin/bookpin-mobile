package com.phase.bookpin.data.api.auth

interface AuthRemoteDataSource {
    suspend fun login(request: SocialAuthTokenRequest): Result<LoginResponse>
}
