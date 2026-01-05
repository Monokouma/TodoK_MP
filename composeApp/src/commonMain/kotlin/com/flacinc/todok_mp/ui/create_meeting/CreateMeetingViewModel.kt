package com.flacinc.todok_mp.ui.create_meeting

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flacinc.todok_mp.domain.meeting.model.CreateMeetingFormState
import com.flacinc.todok_mp.domain.meeting.model.MeetingPlace
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlin.collections.emptyList
import kotlin.time.Clock

@Stable
class CreateMeetingViewModel(

) : ViewModel() {

    var state by mutableStateOf(
        CreateMeetingFormState(
            timestamp = Clock.System.now().epochSeconds,
            meetingPlace = MeetingPlace.ROOM_200,
            subject = "",
            participants = persistentListOf()
        )
    )
        private set

    fun updateField(update: CreateMeetingFormState.() -> CreateMeetingFormState) {
        state = state.update()
    }

    fun submit() {
        viewModelScope.launch {

        }
    }
}