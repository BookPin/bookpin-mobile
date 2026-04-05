package com.phase.bookpin.search.preview

data class BookPreviewState(
    val title: String = "",
    val author: String = "",
    val totalPage: String = "",
    val imageUrl: String = "",
    val isbn: String = "",
    val isLoading: Boolean = false,
) {
    val isSubmitEnabled: Boolean
        get() = title.isNotBlank() &&
            author.isNotBlank() &&
            totalPage.toIntOrNull()?.let { it > 0 } == true &&
            !isLoading
}
