package com.phase.bookpin.bookmark.add

expect class ImageFileReader {
    suspend fun readBytes(uri: String): ByteArray

    fun getExtension(uri: String): String
}
