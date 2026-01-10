package com.flacinc.data.meeting.mapper

import com.flacinc.domain.meeting.entity.MeetingEntity
import com.flacinc.todokmp.data.database.sql.Meeting


fun Meeting.toEntity(): MeetingEntity {
    return MeetingEntity(
        id = id,
        title = title,
        subject = subject,
        timestamp = timestamp,
        room = room,
        participants = participants
    )
}


