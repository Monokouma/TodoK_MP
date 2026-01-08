package com.flacinc.ui.home.mapper

import com.flacinc.domain.meeting.entity.MeetingEntity
import com.flacinc.ui.home.model.UiMeeting
import com.flacinc.ui.utils.model.MeetingPlace
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

    val hour = dateTime.hour.toString().padStart(2, '0')
    val minutes = dateTime.minute.toString().padStart(2, '0')
    val day = dateTime.dayOfMonth.toString().padStart(2, '0')
    val month = dateTime.monthNumber.toString().padStart(2, '0')
    val year = dateTime.year

    return "$day/$month/$year $hour:$minutes"
}


