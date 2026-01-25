package com.phase.bookpin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
