package com.phase.bookpin.data.api.datastore

import kotlinx.coroutines.flow.Flow

interface BookPinPreferenceDataStore {
    fun getString(key: DataStoreKey): Flow<String?>
    suspend fun saveString(key: DataStoreKey, value: String)
    suspend fun remove(key: DataStoreKey)
    suspend fun clear()
}
