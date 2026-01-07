package com.flacinc.todok_mp.domain.meeting.mapper

import com.flacinc.todok_mp.domain.meeting.entity.MeetingEntity
import com.flacinc.todok_mp.ui.home.model.UiMeeting
import com.flacinc.todok_mp.ui.utils.model.MeetingPlace
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun MeetingEntity.toUi() = UiMeeting(
    id = id,
    title = title,
    subject = subject,
    timestamp = formatTimestamp(timestamp),
    place = MeetingPlace.valueOf(place),
    participants = participants.split(",").toPersistentList()
)

fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = dateTime.dayOfMonth.toString().padStart(2, '0')
    val month = dateTime.monthNumber.toString().padStart(2, '0')
    val year = dateTime.year

    return "$day/$month/$year"  // "07/01/2026"
}