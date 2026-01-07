package com.flacinc.todok_mp.domain.meeting.entity

import com.flacinc.todok_mp.domain.meeting.model.MeetingPlace

data class MeetingCreationEntity(
    val title: String,
    val subject: String,
    val timestamp: Long,
    val participants: List<String>,
    val meetingPlace: MeetingPlace,
)
