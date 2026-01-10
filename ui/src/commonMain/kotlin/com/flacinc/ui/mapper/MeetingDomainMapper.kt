package com.flacinc.ui.mapper

import com.flacinc.domain.meeting.entity.MeetingEntity
import com.flacinc.ui.model.UiMeeting
import com.flacinc.ui.utils.formatTimestamp
import com.flacinc.ui.utils.model.MeetingPlace
import kotlinx.collections.immutable.toPersistentList


fun MeetingEntity.toUi() = UiMeeting(
    id = id,
    title = title,
    subject = subject,
    timestamp = formatTimestamp(timestamp),
    room = MeetingPlace.valueOf(room),
    participants = participants.split(",").toPersistentList()
)



