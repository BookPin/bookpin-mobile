package com.phase.bookpin.domain.device

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetOrCreateDeviceIdUseCase(
    private val repository: DeviceRepository,
) {
    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke(): String {
        repository.getDeviceId().onSuccess { deviceId ->
            return deviceId
        }

        val newDeviceId = Uuid.random().toString()
        repository.saveDeviceId(newDeviceId)

        return newDeviceId
    }
}
