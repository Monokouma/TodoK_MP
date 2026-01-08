package com.flacinc.data.meeting.mapper

import com.flacinc.data.meeting.dto.CreateMeetingDto
import com.flacinc.domain.meeting.entity.CreateMeetingEntity


fun CreateMeetingEntity.toDto(): CreateMeetingDto = CreateMeetingDto(
    title = title,
    subject = subject,
    timestamp = timestamp,
    participants = participants,
    meetingPlace = meetingPlace
)
