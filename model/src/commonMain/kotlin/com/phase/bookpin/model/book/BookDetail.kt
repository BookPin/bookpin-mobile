package com.phase.bookpin.model.book

data class BookDetail(
    val id: Long = 0,
    val title: String = "",
    val author: String = "",
    val imageUrl: String = "",
    val totalPage: Int = 0,
    val currentPage: Int = 0,
    val progress: Double = 0.0,
    val isCompleted: Boolean = false,
)
