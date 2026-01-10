package com.flacinc.domain.meeting


import com.flacinc.domain.meeting.entity.MeetingEntity
import kotlinx.coroutines.flow.Flow

class GetMeetingsUseCase(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke(): Flow<List<MeetingEntity>> = meetingRepository.getMeetings()
}
