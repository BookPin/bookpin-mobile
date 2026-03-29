package com.phase.bookpin.bookmark.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class ImageCropper(
    private val onLaunch: (String) -> Unit,
) {
    actual fun launch(imageUri: String) = onLaunch(imageUri)
}

@Composable
actual fun rememberImageCropper(
    onImageCropped: (String?) -> Unit,
): ImageCropper = remember {
    ImageCropper { /* iOS: not yet implemented */ }
}
