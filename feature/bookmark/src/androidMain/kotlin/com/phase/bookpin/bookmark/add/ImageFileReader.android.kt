package com.phase.bookpin.bookmark.add

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap

actual class ImageFileReader(
    private val context: Context,
) {
    actual suspend fun readBytes(uri: String): ByteArray {
        val inputStream = context.contentResolver.openInputStream(Uri.parse(uri))
            ?: throw IllegalStateException("Cannot open input stream for uri: $uri")
        return inputStream.use { it.readBytes() }
    }

    actual fun getExtension(uri: String): String {
        val contentUri = Uri.parse(uri)
        val mimeType = context.contentResolver.getType(contentUri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
    }
}
