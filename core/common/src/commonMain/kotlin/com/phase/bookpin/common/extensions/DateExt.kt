package com.phase.bookpin.common.extensions

import kotlinx.datetime.LocalDateTime

fun String.toFormattedDate(): String = runCatching {
    val dateTime = LocalDateTime.parse(this)
    "${dateTime.year}년 ${dateTime.monthNumber}월 ${dateTime.dayOfMonth}일"
}.getOrDefault(this)
