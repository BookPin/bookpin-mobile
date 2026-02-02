package com.phase.bookpin.model

sealed interface SessionEvent {
    data object SessionExpired : SessionEvent
    data object InvalidRefreshToken : SessionEvent
}
