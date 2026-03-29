package com.phase.bookpin.data.api.user

interface UserRemoteDataSource {
    suspend fun getUser(): Result<UserResponse>
}
