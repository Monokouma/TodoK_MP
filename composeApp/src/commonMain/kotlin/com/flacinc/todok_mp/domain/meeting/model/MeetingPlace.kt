package com.flacinc.todok_mp.domain.meeting.model

import androidx.compose.ui.graphics.Color
import com.flacinc.todok_mp.ui.theme.Room200Color
import com.flacinc.todok_mp.ui.theme.Room404Color
import com.flacinc.todok_mp.ui.theme.Room500Color
import org.jetbrains.compose.resources.StringResource
import todok_mp.composeapp.generated.resources.Res
import todok_mp.composeapp.generated.resources.room_202_name
import todok_mp.composeapp.generated.resources.room_404_name
import todok_mp.composeapp.generated.resources.room_500_name

enum class MeetingPlace(val resourceNameId: StringResource, val colorResource: Color) {
    ROOM_200(Res.string.room_202_name, Room200Color),
    ROOM_404(Res.string.room_404_name, Room404Color),
    ROOM_500(Res.string.room_500_name, Room500Color),
}