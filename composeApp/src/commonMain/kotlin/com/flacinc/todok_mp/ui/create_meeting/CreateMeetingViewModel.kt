package com.flacinc.todok_mp.ui.create_meeting

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flacinc.todok_mp.domain.meeting.model.MeetingPlace
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

) : ViewModel() {

    private val _uiState = MutableStateFlow<CreateMeetingFormState>(
        CreateMeetingFormState(
            timestamp = Clock.System.now().epochSeconds,
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

            /*
            createMeetingUseCase(_uiState.value)
                .onSuccess {
                    _onSuccess.emit(Unit)
                }
                .onFailure { e ->
                    _uiState.update { it.copy(errorMessage = e.message ?: "Erreur inconnue") }
                }

                */

            _uiState.update { it.copy(isLoading = false) }
        }
    }


}