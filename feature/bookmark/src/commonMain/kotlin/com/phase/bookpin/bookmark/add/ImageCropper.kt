package com.phase.bookpin.bookmark.add

import androidx.compose.runtime.Composable

expect class ImageCropper {
    fun launch(imageUri: String)
}

@Composable
expect fun rememberImageCropper(
    onImageCropped: (String?) -> Unit,
): ImageCropper
