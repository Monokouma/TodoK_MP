package com.flacinc.todok_mp.domain.meeting.entity


data class CreateMeetingEntity(
    val title: String,
    val subject: String,
    val timestamp: Long,
    val participants: List<String>,
    val meetingPlace: String,
)


