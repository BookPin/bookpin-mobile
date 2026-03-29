package com.phase.bookpin.bookmark.add

actual class TextRecognizer {
    actual suspend fun recognizeText(imageUri: String): Result<String> =
        Result.failure(NotImplementedError("iOS TextRecognizer not yet implemented"))

    actual fun close() { /* no-op */ }
}
