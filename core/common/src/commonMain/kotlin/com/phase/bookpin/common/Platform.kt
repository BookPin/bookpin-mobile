package com.phase.bookpin.common

expect fun platform(): Platform

sealed interface Platform {
    data object Android : Platform
    data object iOS : Platform
}
