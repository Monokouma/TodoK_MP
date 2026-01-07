package com.flacinc.todok_mp.data.meeting.dto

data class CreateMeetingDto(
    val title: String,
    val subject: String,
    val timestamp: Long,
    val participants: List<String>,
    val meetingPlace: String,
)
