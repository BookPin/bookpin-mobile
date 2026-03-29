package com.phase.bookpin.bookmark.add

expect class TextRecognizer {
    suspend fun recognizeText(imageUri: String): Result<String>
    fun close()
}
