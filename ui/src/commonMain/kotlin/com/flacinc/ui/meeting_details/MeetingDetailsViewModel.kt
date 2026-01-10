package com.flacinc.ui.meeting_details

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flacinc.domain.meeting.GetMeetingByIdUseCase
import com.flacinc.ui.mapper.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Stable
class MeetingDetailsViewModel(
    private val getMeetingByIdUseCase: GetMeetingByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<MeetingDetailsState>(MeetingDetailsState.Error)
    val uiState = _uiState.asStateFlow()
    fun onDetailsLoad(meetingId: Long) {
        viewModelScope.launch {
            getMeetingByIdUseCase.invoke(meetingId).fold(
                onSuccess = {
                    _uiState.value = MeetingDetailsState.MeetingDetails(meetingDetails = it.toUi())
                },
                onFailure = {
                    _uiState.value = MeetingDetailsState.Error
                }
            )
        }
    }
}