package com.flacinc.todok_mp.data.meeting.mapper

import com.flacinc.todok_mp.data.meeting.dto.MeetingDto
import com.flacinc.todok_mp.domain.meeting.entity.MeetingEntity
import com.flacinc.todokmp.data.database.sql.Meeting

fun Meeting.toDto(): MeetingDto {
    return MeetingDto(
        id = id,
        title = title,
        subject = subject,
        timestamp = timestamp,
        place = place,
        participants = participants
    )
}

fun MeetingDto.toEntity(): MeetingEntity = MeetingEntity(
    id = id,
    title = title,
    subject = subject,
    timestamp = timestamp,
    participants = participants,
    place = place
)


