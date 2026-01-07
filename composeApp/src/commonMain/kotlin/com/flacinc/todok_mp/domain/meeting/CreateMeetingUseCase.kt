package com.flacinc.todok_mp.domain.meeting

import com.flacinc.todok_mp.domain.error_manager.TodokError
import com.flacinc.todok_mp.domain.error_manager.TodokException
import com.flacinc.todok_mp.domain.meeting.entity.CreateMeetingEntity

class CreateMeetingUseCase(
    private val meetingRepository: MeetingRepository
) {

    suspend operator fun invoke(
        meetingTitle: String,
        meetingSubject: String,
        meetingTimeStamp: Long,
        meetingPlace: String,
        participants: List<String>
    ): Result<Unit> {
        when {
            meetingTitle.isEmpty() -> return Result.failure(TodokException(TodokError.MEETING_TITLE_EMPTY_VALUE))
            meetingSubject.isEmpty() -> return Result.failure(TodokException(TodokError.MEETING_SUBJECT_EMPTY_VALUE))
            participants.isEmpty() -> return Result.failure(TodokException(TodokError.MEETING_PARTICIPANTS_EMPTY_VALUE))
        }

        return meetingRepository.save(
            CreateMeetingEntity(
                title = meetingTitle,
                subject = meetingSubject,
                timestamp = meetingTimeStamp,
                participants = participants,
                meetingPlace = meetingPlace
            )
        )
    }
}