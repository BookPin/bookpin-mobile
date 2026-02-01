package com.phase.bookpin.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

internal const val DATA_STORE_FILE_NAME = "bookpin.preferences_pb"

expect fun createDataStore(): DataStore<Preferences>
