package com.flacinc.todok_mp.data.meeting.mapper

import com.flacinc.todok_mp.data.meeting.dto.CreateMeetingDto
import com.flacinc.todok_mp.domain.meeting.entity.CreateMeetingEntity

fun CreateMeetingEntity.toDto(): CreateMeetingDto = CreateMeetingDto(
    title = title,
    subject = subject,
    timestamp = timestamp,
    participants = participants,
    meetingPlace = meetingPlace
)
