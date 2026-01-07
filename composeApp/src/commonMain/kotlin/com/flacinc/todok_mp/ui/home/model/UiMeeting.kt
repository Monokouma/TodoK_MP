package com.flacinc.todok_mp.ui.home.model

import com.flacinc.todok_mp.ui.utils.model.MeetingPlace
import kotlinx.collections.immutable.PersistentList

data class UiMeeting(
    val id: Long,
    val title: String,
    val subject: String,
    val timestamp: String,
    val place: MeetingPlace,
    val participants: PersistentList<String>
)
