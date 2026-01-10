package com.flacinc.ui.home

import com.flacinc.ui.model.UiMeeting
import kotlinx.collections.immutable.PersistentList

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data object Error : HomeUiState()
    data class ShowMeetings(val meetings: PersistentList<UiMeeting>) : HomeUiState()

    data object EmptyMeetings : HomeUiState()
}
