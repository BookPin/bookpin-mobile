package com.phase.bookpin.data.session

import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.data.api.session.SessionEventDataSource
import com.phase.bookpin.domain.session.SessionRepository
import com.phase.bookpin.model.SessionEvent
import kotlinx.coroutines.flow.Flow

class SessionRepositoryImpl(
    private val sessionEventDataSource: SessionEventDataSource,
    private val preferenceDataStore: BookPinPreferenceDataStore,
) : SessionRepository {

    override val events: Flow<SessionEvent> = sessionEventDataSource.events

    override suspend fun clearSession() {
        preferenceDataStore.clear()
    }
}
