package com.phase.bookpin.search.preview

sealed interface BookPreviewSideEffect {
    data object NavigateBack : BookPreviewSideEffect
    data object NavigateClose : BookPreviewSideEffect
    data object NavigateToHome : BookPreviewSideEffect
    data class ShowSnackbar(
        val message: String,
    ) : BookPreviewSideEffect
}
