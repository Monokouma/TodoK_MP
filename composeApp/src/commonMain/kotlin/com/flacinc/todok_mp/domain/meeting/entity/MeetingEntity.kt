package com.flacinc.todok_mp.domain.meeting.entity

data class MeetingEntity(
    val id: Long,
    val title: String,
    val subject: String,
    val timestamp: Long,
    val place: String,
    val participants: String,
)
