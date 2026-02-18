package com.phase.bookpin.data.remote.auth

import com.phase.bookpin.data.api.auth.SessionDataSource
import com.phase.bookpin.model.auth.SessionEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class SessionDataSourceImpl : SessionDataSource {
    private val _events = Channel<SessionEvent>(Channel.BUFFERED)
    override val events: Flow<SessionEvent> = _events.receiveAsFlow()

    override fun emitSessionExpired() {
        _events.trySend(SessionEvent.SessionExpired)
    }

    override fun emitInvalidRefreshToken() {
        _events.trySend(SessionEvent.InvalidRefreshToken)
    }
}
