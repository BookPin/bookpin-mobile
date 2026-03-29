package com.phase.bookpin.bookmark.add

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
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
    val context = androidx.compose.ui.platform.LocalContext.current
    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri: Uri? ->
        onImagePicked(uri?.toString())
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success: Boolean ->
        if (success) {
            onImagePicked(pendingCameraUri?.toString())
        } else {
            onImagePicked(null)
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted: Boolean ->
        if (granted) {
            val uri = createImageUri(context)
            pendingCameraUri = uri
            cameraLauncher.launch(uri)
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
                    val uri = createImageUri(context)
                    pendingCameraUri = uri
                    cameraLauncher.launch(uri)
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
