package com.phase.bookpin.domain.auth

import com.phase.bookpin.model.auth.SessionEvent
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    val events: Flow<SessionEvent>
    suspend fun clearSession()
}
