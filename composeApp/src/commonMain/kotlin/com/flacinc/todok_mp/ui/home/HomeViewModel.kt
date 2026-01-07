package com.flacinc.todok_mp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flacinc.todok_mp.domain.meeting.GetMeetingsUseCase
import com.flacinc.todok_mp.ui.home.model.UiMeeting
import com.flacinc.todok_mp.ui.utils.model.SortOrder
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val getMeetingsUseCase: GetMeetingsUseCase
) : ViewModel() {

    private val sortOrder = MutableStateFlow(SortOrder.NAME_ASC)

    val uiState: StateFlow<HomeUiState> = combine(
        getMeetingsUseCase(),
        sortOrder
    ) { meetings, sortOrder ->
        if (meetings.isEmpty()) {
            HomeUiState.EmptyMeetings
        } else {
            HomeUiState.ShowMeetings(applySortOrder(meetings, sortOrder))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState.Loading
    )

    fun updateSortOrder(order: SortOrder) {
        sortOrder.value = order
    }

    private fun applySortOrder(
        meetings: List<UiMeeting>,
        sortOrder: SortOrder
    ): PersistentList<UiMeeting> = when (sortOrder) {
        SortOrder.NAME_ASC -> meetings.sortedBy { it.title }
        SortOrder.NAME_DESC -> meetings.sortedByDescending { it.title }
        SortOrder.DATE_ASC -> meetings.sortedBy { it.timestamp }
        SortOrder.DATE_DESC -> meetings.sortedByDescending { it.timestamp }
    }.toPersistentList()

}