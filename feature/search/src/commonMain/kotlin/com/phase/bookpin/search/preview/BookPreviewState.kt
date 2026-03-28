package com.phase.bookpin.search.preview

data class BookPreviewState(
    val title: String = "",
    val author: String = "",
    val totalPage: Int = -1,
    val imageUrl: String = "",
    val isbn: String = "",
    val isLoading: Boolean = false,
) {
    val isSubmitEnabled: Boolean
        get() = title.isNotBlank() && author.isNotBlank() && totalPage > 0 && !isLoading
}
