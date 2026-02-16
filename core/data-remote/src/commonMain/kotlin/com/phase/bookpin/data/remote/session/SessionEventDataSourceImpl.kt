package com.phase.bookpin.data.remote.session

import com.phase.bookpin.data.api.session.SessionEventDataSource
import com.phase.bookpin.model.SessionEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SessionEventDataSourceImpl : SessionEventDataSource {
    private val _events = MutableSharedFlow<SessionEvent>(extraBufferCapacity = 1)
    override val events: Flow<SessionEvent> = _events.asSharedFlow()

    override fun emitSessionExpired() {
        _events.tryEmit(SessionEvent.SessionExpired)
    }

    override fun emitInvalidRefreshToken() {
        _events.tryEmit(SessionEvent.InvalidRefreshToken)
    }
}
