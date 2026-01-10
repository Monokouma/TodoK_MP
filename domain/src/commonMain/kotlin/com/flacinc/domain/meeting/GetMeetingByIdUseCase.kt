package com.flacinc.domain.meeting

import com.flacinc.domain.meeting.entity.MeetingEntity

class GetMeetingByIdUseCase(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(meetingId: Long): Result<MeetingEntity> =
        meetingRepository.getMeetingById(meetingId)
}