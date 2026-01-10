package com.flacinc.ui.create_meeting

import androidx.compose.runtime.Immutable
import com.flacinc.ui.utils.model.MeetingPlace
import kotlinx.collections.immutable.PersistentList
import org.jetbrains.compose.resources.StringResource

@Immutable
data class CreateMeetingFormState(
    val timestamp: Long,
    val room: MeetingPlace,
    val subject: String,
    val title: String,
    val participants: PersistentList<String>,
    val isLoading: Boolean,
    val errorMessage: StringResource?,
)