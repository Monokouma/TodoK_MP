package com.flacinc.todok_mp.domain.meeting

import com.flacinc.todok_mp.domain.meeting.entity.MeetingEntity
import kotlinx.coroutines.flow.Flow

class GetMeetingsUseCase(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(): Flow<List<MeetingEntity>> = meetingRepository.getMeetings()
}
