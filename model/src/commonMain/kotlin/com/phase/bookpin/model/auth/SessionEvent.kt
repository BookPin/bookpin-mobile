package com.phase.bookpin.model.auth

sealed interface SessionEvent {
    data object SessionExpired : SessionEvent
    data object InvalidRefreshToken : SessionEvent
}
