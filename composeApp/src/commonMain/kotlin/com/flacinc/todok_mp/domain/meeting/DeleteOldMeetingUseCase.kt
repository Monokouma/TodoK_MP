package com.flacinc.todok_mp.domain.meeting

import kotlinx.coroutines.flow.first
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

class DeleteOldMeetingUseCase(
    private val meetingRepository: MeetingRepository,
) {
    suspend operator fun invoke() {
        val now = Clock.System.now()

        val meetings = meetingRepository.getMeetings().first()

        val toDeleteIds = meetings.filter { meeting ->
            meeting.timestamp + 30.minutes.inWholeMilliseconds < now.toEpochMilliseconds()
        }.map { meeting ->
            meeting.id
        }

        toDeleteIds.forEach { id ->
            meetingRepository.delete(id)
        }
    }
}