package com.flacinc.todok_mp.domain.meeting.model

import org.jetbrains.compose.resources.StringResource
import todok_mp.composeapp.generated.resources.Res
import todok_mp.composeapp.generated.resources.home_no_meeting_title
import todok_mp.composeapp.generated.resources.room_202_name
import todok_mp.composeapp.generated.resources.room_404_name
import todok_mp.composeapp.generated.resources.room_500_name

enum class MeetingPlace(val resourceNameId: StringResource) {
    ROOM_200(Res.string.room_202_name),
    ROOM_404(Res.string.room_404_name),
    ROOM_500(Res.string.room_500_name),
}