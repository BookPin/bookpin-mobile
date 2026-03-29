package com.phase.bookpin.bookmark.add

import androidx.compose.runtime.Composable

expect class ImagePickerLauncher {
    fun launchCamera()
    fun launchGallery()
}

@Composable
expect fun rememberImagePickerLauncher(
    onImagePicked: (String?) -> Unit,
): ImagePickerLauncher
