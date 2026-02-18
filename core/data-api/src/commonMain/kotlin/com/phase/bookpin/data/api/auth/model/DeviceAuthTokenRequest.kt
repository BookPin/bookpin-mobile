package com.phase.bookpin.data.api.auth.model

import com.phase.bookpin.model.auth.DeviceAuthToken
import kotlinx.serialization.Serializable

@Serializable
data class DeviceAuthTokenRequest(
    val deviceId: String,
) {
    companion object {
        fun from(model: DeviceAuthToken) = DeviceAuthTokenRequest(
            deviceId = model.deviceId,
        )
    }
}
