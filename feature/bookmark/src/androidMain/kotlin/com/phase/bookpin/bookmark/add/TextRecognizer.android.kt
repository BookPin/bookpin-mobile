package com.phase.bookpin.bookmark.add

import android.content.Context
import androidx.core.net.toUri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class TextRecognizer(
    private val context: Context,
) {
    private val recognizer = TextRecognition.getClient(
        KoreanTextRecognizerOptions.Builder().build(),
    )

    actual suspend fun recognizeText(imageUri: String): Result<String> =
        suspendCancellableCoroutine { continuation ->
            val image = InputImage.fromFilePath(context, imageUri.toUri())
            val task = recognizer.process(image)
            task.addOnSuccessListener { visionText ->
                continuation.resume(Result.success(visionText.text))
            }
            task.addOnFailureListener { e ->
                continuation.resume(Result.failure(e))
            }
        }

    actual fun close() {
        recognizer.close()
    }
}
