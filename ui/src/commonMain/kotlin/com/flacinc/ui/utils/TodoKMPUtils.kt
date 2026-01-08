package com.flacinc.ui.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

fun millisToTimeString(millis: Long): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val hour = localDateTime.hour
    val minute = localDateTime.minute

    return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
}


fun timeStringToMillis(
    timeString: String,
    baseMillis: Long = Clock.System.now().toEpochMilliseconds()
): Long {
    val baseInstant = Instant.fromEpochMilliseconds(baseMillis)
    val baseDateTime = baseInstant.toLocalDateTime(TimeZone.currentSystemDefault())

    val parts = timeString.split(":")
    val hour = parts[0].toInt()
    val minute = parts[1].toInt()

    val newDateTime = LocalDateTime(
        year = baseDateTime.year,
        monthNumber = baseDateTime.monthNumber,
        dayOfMonth = baseDateTime.dayOfMonth,
        hour = hour,
        minute = minute,
        second = 0,
        nanosecond = 0
    )

    return newDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}