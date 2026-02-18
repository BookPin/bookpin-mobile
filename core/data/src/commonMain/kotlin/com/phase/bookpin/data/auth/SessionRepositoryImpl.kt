package com.phase.bookpin.data.auth

import com.phase.bookpin.data.api.auth.SessionDataSource
import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.domain.auth.SessionRepository
import com.phase.bookpin.model.auth.SessionEvent
import kotlinx.coroutines.flow.Flow

class SessionRepositoryImpl(
    sessionDataSource: SessionDataSource,
    private val preferenceDataStore: BookPinPreferenceDataStore,
) : SessionRepository {

    override val events: Flow<SessionEvent> = sessionDataSource.events

    override suspend fun clearSession() {
        preferenceDataStore.clear()
    }
}
