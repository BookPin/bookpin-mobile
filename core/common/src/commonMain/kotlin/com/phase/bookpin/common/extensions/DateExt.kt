package com.phase.bookpin.common.extensions

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

fun String.toFormattedDate(): String = runCatching {
    val dateTime = LocalDateTime.parse(this)
    "${dateTime.year}년 ${dateTime.month.number}월 ${dateTime.day}일"
}.getOrDefault(this)
