package com.phase.bookpin.bookmark.add

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.posix.memcpy

actual class ImageFileReader {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun readBytes(uri: String): ByteArray {
        val nsUrl = NSURL.fileURLWithPath(uri)
        val data = NSData.dataWithContentsOfURL(nsUrl)
            ?: throw IllegalStateException("Cannot read file at path: $uri")
        return ByteArray(data.length.toInt()).apply {
            usePinned { pinned ->
                memcpy(pinned.addressOf(0), data.bytes, data.length)
            }
        }
    }

    actual fun getExtension(uri: String): String =
        uri.substringAfterLast('.', "jpg").lowercase()
}
