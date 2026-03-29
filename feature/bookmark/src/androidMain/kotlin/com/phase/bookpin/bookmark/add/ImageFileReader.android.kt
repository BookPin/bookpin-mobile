package com.phase.bookpin.bookmark.add

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap

actual class ImageFileReader(
    private val context: Context,
) {
    actual fun getExtension(uri: String): String {
        val contentUri = Uri.parse(uri)
        val mimeType = context.contentResolver.getType(contentUri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
    }
}
