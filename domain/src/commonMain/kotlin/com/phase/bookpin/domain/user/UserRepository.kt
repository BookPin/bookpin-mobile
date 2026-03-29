package com.phase.bookpin.domain.user

import com.phase.bookpin.model.user.User

interface UserRepository {
    suspend fun getUser(): Result<User>
}
