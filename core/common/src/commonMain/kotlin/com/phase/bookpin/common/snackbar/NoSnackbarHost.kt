package com.phase.bookpin.common.snackbar

object NoSnackbarHost : SnackbarHost {
    override fun showSnackbar(message: String) {
        // No-op
    }
}
