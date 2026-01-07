package com.flacinc.todok_mp.ui.create_meeting

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flacinc.todok_mp.domain.error_manager.TodokError
import com.flacinc.todok_mp.domain.meeting.CreateMeetingUseCase
import com.flacinc.todok_mp.ui.utils.model.MeetingPlace
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
            meetingPlace = MeetingPlace.ROOM_200,
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
        _uiState.update { it.copy(meetingPlace = value) }
    }

    fun updateTimestamp(value: Long) {
        println(value)
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
                meetingPlace = _uiState.value.meetingPlace.name,
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
        TodokError.MEETING_TITLE_EMPTY_VALUE.message -> "Le titre ne peut pas être vide"
        TodokError.MEETING_SUBJECT_EMPTY_VALUE.message -> "Le sujet ne peut pas être vide"
        TodokError.MEETING_PARTICIPANTS_EMPTY_VALUE.message -> "Vous devez ajouter au moins un participant"
        else -> "Erreur inconnue"
    }

}