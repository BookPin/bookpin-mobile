package com.phase.bookpin.data.api.user

import com.phase.bookpin.model.user.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val nickname: String = "",
    val profileImageUrl: String = "",
) {
    fun toUser(): User = User(
        id = id,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
    )
}
