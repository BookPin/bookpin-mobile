package com.phase.bookpin.domain.auth

import com.phase.bookpin.model.auth.DeviceAuthToken

interface AuthRepository {
    suspend fun login(token: DeviceAuthToken): Result<Unit>
    suspend fun hasAccessToken(): Boolean
}
