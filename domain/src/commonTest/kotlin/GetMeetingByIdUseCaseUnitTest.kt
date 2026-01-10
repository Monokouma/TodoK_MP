package com.flacinc.todok_mp.domain.meetings

import assertk.assertThat
import assertk.assertions.hasMessage
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.flacinc.domain.meeting.GetMeetingByIdUseCase
import com.flacinc.domain.meeting.MeetingRepository
import com.flacinc.domain.meeting.entity.MeetingEntity
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetMeetingByIdUseCaseUnitTest() {
    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `nominal case - should return meeting details entity`() = runTest(testDispatcher) {
        val repository = mock<MeetingRepository>()
        val useCase = GetMeetingByIdUseCase(repository)

        everySuspend {
            repository.getMeetingById(DEFAULT_MEETING_ID)
        } returns Result.success(DEFAULT_MEETING_DETAILS_ENTITY)

        val result = useCase(DEFAULT_MEETING_ID)

        result.onSuccess { meetingDetailsEntity ->
            assertThat(meetingDetailsEntity).isEqualTo(
                DEFAULT_MEETING_DETAILS_ENTITY
            )
        }
    }

    @Test
    fun `error case - should return failure`() = runTest(testDispatcher) {
        val repository = mock<MeetingRepository>()
        val useCase = GetMeetingByIdUseCase(repository)

        everySuspend {
            repository.getMeetingById(DEFAULT_MEETING_ID)
        } returns Result.failure(Exception("Error"))

        val result = useCase(DEFAULT_MEETING_ID)

        result.onFailure { failure ->
            assertThat(failure).isInstanceOf<Exception>().hasMessage("Error")
        }
    }

    companion object {
        private const val DEFAULT_MEETING_ID = 13L
        private const val DEFAULT_MEETING_TITLE = "Daily"
        private const val DEFAULT_MEETING_SUBJECT = "Standup"
        private const val DEFAULT_MEETING_TIMESTAMP = 123456789L
        private const val DEFAULT_MEETING_ROOM = "ROOM_202"
        private const val DEFAULT_MEETING_PARTICIPANTS = "Alice,Bob"

        private val DEFAULT_MEETING_DETAILS_ENTITY = MeetingEntity(
            id = DEFAULT_MEETING_ID,
            title = DEFAULT_MEETING_TITLE,
            subject = DEFAULT_MEETING_SUBJECT,
            timestamp = DEFAULT_MEETING_TIMESTAMP,
            room = DEFAULT_MEETING_ROOM,
            participants = DEFAULT_MEETING_PARTICIPANTS,
        )
    }
}