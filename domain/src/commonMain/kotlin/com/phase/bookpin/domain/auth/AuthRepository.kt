package com.phase.bookpin.domain.auth

import com.phase.bookpin.model.SocialAuthToken

interface AuthRepository {
    suspend fun login(token: SocialAuthToken): Result<Unit>
}
