package com.phase.bookpin.common.snackbar

import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackbarHost = staticCompositionLocalOf<SnackbarHost> { NoSnackbarHost }
