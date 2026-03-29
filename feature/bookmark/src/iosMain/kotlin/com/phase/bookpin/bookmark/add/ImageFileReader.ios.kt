package com.phase.bookpin.bookmark.add

actual class ImageFileReader {
    actual fun getExtension(uri: String): String =
        uri.substringAfterLast('.', "jpg").lowercase()
}
