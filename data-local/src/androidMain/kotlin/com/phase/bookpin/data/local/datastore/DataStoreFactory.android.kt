package com.phase.bookpin.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

private var appContext: Context? = null

fun initDataStore(context: Context) {
    appContext = context.applicationContext
}

actual fun createDataStore(): DataStore<Preferences> {
    val context = requireNotNull(appContext) {
        "DataStore not initialized. Call initDataStore(context) first."
    }
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath()
        }
    )
}
