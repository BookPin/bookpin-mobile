package com.phase.bookpin.data.api.auth

interface SessionEventListener {
    fun onSessionExpired()
    fun onInvalidRefreshToken()
}
