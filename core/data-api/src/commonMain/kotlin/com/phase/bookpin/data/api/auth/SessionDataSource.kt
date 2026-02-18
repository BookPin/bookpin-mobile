package com.phase.bookpin.data.api.auth

import com.phase.bookpin.model.auth.SessionEvent
import kotlinx.coroutines.flow.Flow

interface SessionDataSource {
    val events: Flow<SessionEvent>
    fun emitSessionExpired()
    fun emitInvalidRefreshToken()
}
