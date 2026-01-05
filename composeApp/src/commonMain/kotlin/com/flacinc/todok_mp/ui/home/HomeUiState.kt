package com.flacinc.todok_mp.ui.home

sealed class HomeUiState {
    data object Loading: HomeUiState()
    data object Error: HomeUiState()
    data class ShowMeetings(val meetings: List<String>): HomeUiState()

    data object EmptyMeetings: HomeUiState()
}
