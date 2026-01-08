package com.flacinc.ui.home.model

import com.flacinc.ui.utils.model.MeetingPlace
import kotlinx.collections.immutable.PersistentList

data class UiMeeting(
    val id: Long,
    val title: String,
    val subject: String,
    val timestamp: String,
    val place: MeetingPlace,
    val participants: PersistentList<String>
)
