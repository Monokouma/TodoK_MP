package com.flacinc.todok_mp.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(

) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.EmptyMeetings)
    val uiState: StateFlow<HomeUiState> = _uiState


}