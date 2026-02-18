package com.phase.bookpin.data.auth

import com.phase.bookpin.data.api.auth.SessionEventListener
import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.domain.auth.SessionRepository
import com.phase.bookpin.model.auth.SessionEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class SessionRepositoryImpl(
    private val preferenceDataStore: BookPinPreferenceDataStore,
) : SessionRepository, SessionEventListener {
    private val _events = Channel<SessionEvent>(Channel.BUFFERED)
    override val events: Flow<SessionEvent> = _events.receiveAsFlow()

    override fun onSessionExpired() {
        _events.trySend(SessionEvent.SessionExpired)
    }

    override fun onInvalidRefreshToken() {
        _events.trySend(SessionEvent.InvalidRefreshToken)
    }

    override suspend fun clearSession() {
        preferenceDataStore.clear()
    }
}
