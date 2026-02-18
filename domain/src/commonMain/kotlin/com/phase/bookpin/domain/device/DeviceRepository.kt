package com.phase.bookpin.domain.device

interface DeviceRepository {
    suspend fun getDeviceId(): Result<String>
    suspend fun saveDeviceId(deviceId: String)
}
