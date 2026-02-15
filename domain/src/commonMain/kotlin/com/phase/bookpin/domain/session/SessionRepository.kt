package com.phase.bookpin.domain.session

import com.phase.bookpin.model.SessionEvent
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    val events: Flow<SessionEvent>
    suspend fun clearSession()
}
