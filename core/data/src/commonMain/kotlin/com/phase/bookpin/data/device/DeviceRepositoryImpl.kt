package com.phase.bookpin.data.device

import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.data.api.datastore.DataStoreKey
import com.phase.bookpin.domain.device.DeviceRepository
import kotlinx.coroutines.flow.first

class DeviceRepositoryImpl(
    private val preferenceDataStore: BookPinPreferenceDataStore,
) : DeviceRepository {

    override suspend fun getDeviceId(): Result<String> {
        val stored = preferenceDataStore.getString(DataStoreKey.DEVICE_ID).first()
        return if (!stored.isNullOrEmpty()) {
            Result.success(stored)
        } else {
            Result.failure(NoSuchElementException("Device ID not found"))
        }
    }

    override suspend fun saveDeviceId(deviceId: String) {
        preferenceDataStore.saveString(DataStoreKey.DEVICE_ID, deviceId)
    }
}
