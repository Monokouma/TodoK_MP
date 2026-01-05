package com.flacinc.todok_mp.domain.meeting.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class CreateMeetingFormState(
    val timestamp: Long,
    val meetingPlace: MeetingPlace,
    val subject: String,
    val participants: ImmutableList<String>
)
