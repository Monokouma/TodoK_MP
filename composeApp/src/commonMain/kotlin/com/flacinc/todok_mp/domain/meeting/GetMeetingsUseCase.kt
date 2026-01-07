package com.flacinc.todok_mp.domain.meeting

import com.flacinc.todok_mp.domain.meeting.mapper.toUi
import com.flacinc.todok_mp.ui.home.model.UiMeeting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMeetingsUseCase(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke(): Flow<List<UiMeeting>> = meetingRepository.getMeetings().map {
        it.map { meeting ->
            meeting.toUi()
        }
    }
}
