package com.flacinc.domain.meeting

import kotlinx.coroutines.flow.first
import kotlin.time.Clock.System.now
import kotlin.time.Duration.Companion.minutes

class DeleteOldMeetingUseCase(
    private val meetingRepository: MeetingRepository,
) {
    suspend operator fun invoke() {
        meetingRepository
            .getMeetings()
            .first()
            .filter { meeting ->
                meeting.timestamp + 30.minutes.inWholeMilliseconds < now().toEpochMilliseconds()
            }.map { meeting ->
                meeting.id
            }.forEach { id ->
                meetingRepository.delete(id)
            }
    }
}