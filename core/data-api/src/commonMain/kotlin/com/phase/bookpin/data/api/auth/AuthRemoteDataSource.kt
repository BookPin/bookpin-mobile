package com.phase.bookpin.data.api.auth

import com.phase.bookpin.data.api.auth.model.DeviceAuthTokenRequest
import com.phase.bookpin.data.api.auth.model.LoginResponse

interface AuthRemoteDataSource {
    suspend fun login(request: DeviceAuthTokenRequest): Result<LoginResponse>
}
