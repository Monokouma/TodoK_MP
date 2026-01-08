package com.flacinc.data.meeting.dto

data class MeetingDto(
    val id: Long,
    val title: String,
    val subject: String,
    val timestamp: Long,
    val place: String,
    val participants: String,
)
