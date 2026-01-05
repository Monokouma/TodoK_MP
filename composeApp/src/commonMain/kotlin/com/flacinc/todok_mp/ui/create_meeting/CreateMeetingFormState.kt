package com.flacinc.todok_mp.ui.create_meeting

import androidx.compose.runtime.Immutable
import com.flacinc.todok_mp.domain.meeting.model.MeetingPlace
import kotlinx.collections.immutable.PersistentList

@Immutable
data class CreateMeetingFormState(
    val timestamp: Long,
    val meetingPlace: MeetingPlace,
    val subject: String,
    val title: String,
    val participants: PersistentList<String>,
    val isLoading: Boolean,
    val errorMessage: String?,
)