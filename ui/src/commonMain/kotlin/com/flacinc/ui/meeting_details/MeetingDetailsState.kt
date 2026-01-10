package com.flacinc.ui.meeting_details

import com.flacinc.ui.model.UiMeeting

sealed class MeetingDetailsState {
    data object Loading : MeetingDetailsState()
    data object Error : MeetingDetailsState()
    data class MeetingDetails(val meetingDetails: UiMeeting) : MeetingDetailsState()
}
