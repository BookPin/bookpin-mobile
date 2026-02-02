package com.phase.bookpin.data.api.session

import com.phase.bookpin.model.SessionEvent
import kotlinx.coroutines.flow.Flow

interface SessionEventDataSource {
    val events: Flow<SessionEvent>
    fun emitSessionExpired()
    fun emitInvalidRefreshToken()
}
