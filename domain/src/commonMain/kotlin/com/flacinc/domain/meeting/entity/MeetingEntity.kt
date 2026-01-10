package com.flacinc.domain.meeting.entity

data class MeetingEntity(
    val id: Long = 0L,
    val title: String,
    val subject: String,
    val timestamp: Long,
    val room: String,
    val participants: String,
)
