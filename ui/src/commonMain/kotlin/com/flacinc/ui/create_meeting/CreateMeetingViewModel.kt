package com.flacinc.ui.create_meeting

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flacinc.domain.error_manager.TodokError
import com.flacinc.domain.meeting.CreateMeetingUseCase
import com.flacinc.ui.resources.Res
import com.flacinc.ui.resources.error
import com.flacinc.ui.resources.meeting_participants_empty_value
import com.flacinc.ui.resources.meeting_subject_empty_value
import com.flacinc.ui.resources.meeting_title_empty_value
import com.flacinc.ui.utils.model.MeetingPlace
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock

@Stable
class CreateMeetingViewModel(
    private val createMeetingUseCase: CreateMeetingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CreateMeetingFormState(
            timestamp = Clock.System.now().toEpochMilliseconds(),
            room = MeetingPlace.ROOM_200,
            subject = "",
            title = "",
            participants = persistentListOf(),
            errorMessage = null,
            isLoading = false
        )
    )

    private val _onSuccess = MutableSharedFlow<Unit>()
    val onSuccess = _onSuccess.asSharedFlow()

    val uiState = _uiState.asStateFlow()

    fun updateSubject(value: String) {
        _uiState.update { it.copy(subject = value) }
    }

    fun updateTitle(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun updateMeetingPlace(value: MeetingPlace) {
        _uiState.update { it.copy(room = value) }
    }

    fun updateTimestamp(value: Long) {
        _uiState.update { it.copy(timestamp = value) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun updateParticipants(value: String) {

        _uiState.update {
            it.copy(
                participants = it.participants.add(
                    "${
                        value.lowercase().replace(" ", "-")
                    }@lamzone.fr"
                )
            )
        }
    }

    fun submit() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            createMeetingUseCase(
                meetingTitle = _uiState.value.title,
                meetingSubject = _uiState.value.subject,
                meetingPlace = _uiState.value.room.name,
                meetingTimeStamp = _uiState.value.timestamp,
                participants = _uiState.value.participants.toList(),

                ).fold(
                onSuccess = { success ->
                    _onSuccess.emit(success)
                },
                onFailure = { failure ->
                    _uiState.update { it.copy(errorMessage = transformErrorMessage(failure)) }
                }
            )

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun transformErrorMessage(error: Throwable) = when (error.message) {
        TodokError.MEETING_TITLE_EMPTY_VALUE.message -> Res.string.meeting_title_empty_value
        TodokError.MEETING_SUBJECT_EMPTY_VALUE.message -> Res.string.meeting_subject_empty_value
        TodokError.MEETING_PARTICIPANTS_EMPTY_VALUE.message -> Res.string.meeting_participants_empty_value
        else -> Res.string.error
    }

}