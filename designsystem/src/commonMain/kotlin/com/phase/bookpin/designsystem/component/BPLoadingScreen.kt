package com.phase.bookpin.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.phase.bookpin.designsystem.BookPinTheme

@Composable
fun BPLoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BookPinTheme.colors.bgCanvas),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = BookPinTheme.colors.buttonPrimary,
            trackColor = BookPinTheme.colors.bgSurface,
        )
    }
}
