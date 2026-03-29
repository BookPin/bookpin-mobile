package com.phase.bookpin.bookmark.add

expect class ImageFileReader {
    fun getExtension(uri: String): String
}
