package com.phase.bookpin.data.remote.client

import kotlinx.serialization.json.Json

internal val clientJson = Json {
    encodeDefaults = true
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
}
