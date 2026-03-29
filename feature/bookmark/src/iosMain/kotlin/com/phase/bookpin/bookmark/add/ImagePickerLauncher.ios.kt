package com.phase.bookpin.bookmark.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class ImagePickerLauncher(
    private val onLaunchCamera: () -> Unit,
    private val onLaunchGallery: () -> Unit,
) {
    actual fun launchCamera() = onLaunchCamera()
    actual fun launchGallery() = onLaunchGallery()
}

@Composable
actual fun rememberImagePickerLauncher(
    onImagePicked: (String?) -> Unit,
): ImagePickerLauncher = remember {
    ImagePickerLauncher(
        onLaunchCamera = { /* iOS: not yet implemented */ },
        onLaunchGallery = { /* iOS: not yet implemented */ },
    )
}
