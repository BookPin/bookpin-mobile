package com.phase.bookpin.data.api.datastore

enum class DataStoreKey(val key: String) {
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token"),
    REFRESH_TOKEN_EXPIRED_AT("refresh_token_expired_at"),
    DEVICE_ID("device_id"),
    USER_ID("user_id"),
    USER_NICKNAME("user_nickname"),
    USER_PROFILE_IMAGE_URL("user_profile_image_url"),
}
