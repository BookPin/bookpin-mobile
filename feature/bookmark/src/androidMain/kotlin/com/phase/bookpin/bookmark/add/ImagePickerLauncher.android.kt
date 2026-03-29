package com.phase.bookpin.bookmark.add

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

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
): ImagePickerLauncher {
    val context = LocalContext.current
    var pendingCameraUri by rememberSaveable { mutableStateOf<String?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri: Uri? ->
        onImagePicked(uri?.toString())
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success: Boolean ->
        if (success) {
            onImagePicked(pendingCameraUri)
        } else {
            onImagePicked(null)
        }
    }

    fun launchCameraWithUri() {
        val uri = createImageUri(context)
        pendingCameraUri = uri.toString()
        cameraLauncher.launch(uri)
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted: Boolean ->
        if (granted) {
            launchCameraWithUri()
        } else {
            onImagePicked(null)
        }
    }

    return remember {
        ImagePickerLauncher(
            onLaunchCamera = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA,
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    launchCameraWithUri()
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            onLaunchGallery = {
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                )
            },
        )
    }
}

private fun createImageUri(context: Context): Uri {
    val imageDir = File(context.cacheDir, "bookmark_images")
    if (!imageDir.exists()) imageDir.mkdirs()
    val imageFile = File(imageDir, "bookmark_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile,
    )
}
