package com.phase.bookpin.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.data.api.datastore.DataStoreKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookPinPreferenceDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : BookPinPreferenceDataStore {

    override fun getString(key: DataStoreKey): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key.key)] ?: ""
        }

    override suspend fun saveString(key: DataStoreKey, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key.key)] = value
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
