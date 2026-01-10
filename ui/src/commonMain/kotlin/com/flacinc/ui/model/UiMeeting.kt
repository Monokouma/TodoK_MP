package com.flacinc.ui.model

import com.flacinc.ui.utils.model.MeetingPlace
import kotlinx.collections.immutable.PersistentList

data class UiMeeting(
    val id: Long,
    val title: String,
    val subject: String,
    val timestamp: String,
    val room: MeetingPlace,
    val participants: PersistentList<String>
)
