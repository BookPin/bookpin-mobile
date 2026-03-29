package com.phase.bookpin.bookmark.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageActivity
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView

actual class ImageCropper(
    private val onLaunch: (String) -> Unit,
) {
    actual fun launch(imageUri: String) = onLaunch(imageUri)
}

private class BookPinCropImageContract : ActivityResultContract<Uri, Uri?>() {
    override fun createIntent(context: Context, input: Uri): Intent =
        Intent(context, CropImageActivity::class.java).apply {
            val bundle = Bundle().apply {
                putParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE, input)
                putParcelable(
                    CropImage.CROP_IMAGE_EXTRA_OPTIONS,
                    CropImageOptions(
                        guidelines = CropImageView.Guidelines.ON,
                        fixAspectRatio = false,
                    ),
                )
            }
            putExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE, bundle)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode != Activity.RESULT_OK || intent == null) return null
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(
                CropImage.CROP_IMAGE_EXTRA_RESULT,
                CropImage.ActivityResult::class.java,
            )
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(CropImage.CROP_IMAGE_EXTRA_RESULT)
        }
        return result?.uriContent
    }
}

@Composable
actual fun rememberImageCropper(
    onImageCropped: (String?) -> Unit,
): ImageCropper {
    val cropLauncher = rememberLauncherForActivityResult(
        contract = BookPinCropImageContract(),
    ) { uri ->
        onImageCropped(uri?.toString())
    }

    return remember {
        ImageCropper { imageUri ->
            cropLauncher.launch(Uri.parse(imageUri))
        }
    }
}
