package com.flacinc.todok_mp.domain.meetings

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isSuccess
import com.flacinc.domain.error_manager.TodokError
import com.flacinc.domain.meeting.CreateMeetingUseCase
import com.flacinc.domain.meeting.MeetingRepository
import com.flacinc.domain.meeting.entity.MeetingEntity
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class CreateMeetingUseCaseUnitTest {
    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `nominal case - create meeting should succeed`() = runTest(testDispatcher) {
        val repository = mock<MeetingRepository>()

        everySuspend {
            repository.save(
                MeetingEntity(
                    title = DEFAULT_MEETING_TITLE,
                    subject = DEFAULT_MEETING_SUBJECT,
                    timestamp = DEFAULT_TIMESTAMP,
                    room = DEFAULT_MEETING_PLACE,
                    participants = DEFAULT_PARTICIPANTS_STRING,
                )
            )
        } returns Result.success(Unit)

        val useCase = CreateMeetingUseCase(repository)

        val result = useCase.invoke(
            meetingTitle = DEFAULT_MEETING_TITLE,
            meetingSubject = DEFAULT_MEETING_SUBJECT,
            meetingTimeStamp = DEFAULT_TIMESTAMP,
            meetingPlace = DEFAULT_MEETING_PLACE,
            participants = DEFAULT_PARTICIPANTS_LIST,
        )

        assertThat(result).isSuccess()

        verifySuspend {
            repository.save(
                MeetingEntity(
                    title = DEFAULT_MEETING_TITLE,
                    subject = DEFAULT_MEETING_SUBJECT,
                    timestamp = DEFAULT_TIMESTAMP,
                    room = DEFAULT_MEETING_PLACE,
                    participants = DEFAULT_PARTICIPANTS_STRING,
                )
            )
        }
    }

    @Test
    fun `error case - create meeting should fail`() = runTest(testDispatcher) {
        val repository = mock<MeetingRepository>()

        everySuspend {
            repository.save(
                MeetingEntity(
                    title = DEFAULT_MEETING_TITLE,
                    subject = DEFAULT_MEETING_SUBJECT,
                    timestamp = DEFAULT_TIMESTAMP,
                    room = DEFAULT_MEETING_PLACE,
                    participants = DEFAULT_PARTICIPANTS_STRING,
                )
            )
        } returns Result.failure(Exception(""))

        val useCase = CreateMeetingUseCase(repository)

        val result = useCase.invoke(
            meetingTitle = DEFAULT_MEETING_TITLE,
            meetingSubject = DEFAULT_MEETING_SUBJECT,
            meetingTimeStamp = DEFAULT_TIMESTAMP,
            meetingPlace = DEFAULT_MEETING_PLACE,
            participants = DEFAULT_PARTICIPANTS_LIST,
        )

        assertThat(result).isFailure()

        verifySuspend {
            repository.save(
                MeetingEntity(
                    title = DEFAULT_MEETING_TITLE,
                    subject = DEFAULT_MEETING_SUBJECT,
                    timestamp = DEFAULT_TIMESTAMP,
                    room = DEFAULT_MEETING_PLACE,
                    participants = DEFAULT_PARTICIPANTS_STRING,
                )
            )
        }
    }

    @Test
    fun `error case - empty creation value should fail`() = runTest(testDispatcher) {
        val repository = mock<MeetingRepository>()

        val useCase = CreateMeetingUseCase(repository)

        val emptyTitleResult = useCase.invoke(
            meetingTitle = "",
            meetingSubject = DEFAULT_MEETING_SUBJECT,
            meetingTimeStamp = DEFAULT_TIMESTAMP,
            meetingPlace = DEFAULT_MEETING_PLACE,
            participants = DEFAULT_PARTICIPANTS_LIST,
        )

        assertThat(emptyTitleResult).isFailure()

        emptyTitleResult.onFailure {
            assertThat(it.message).isEqualTo(TodokError.MEETING_TITLE_EMPTY_VALUE.message)
        }

        val emptySubjectResult = useCase.invoke(
            meetingTitle = DEFAULT_MEETING_TITLE,
            meetingSubject = "",
            meetingTimeStamp = DEFAULT_TIMESTAMP,
            meetingPlace = DEFAULT_MEETING_PLACE,
            participants = DEFAULT_PARTICIPANTS_LIST,
        )

        assertThat(emptySubjectResult).isFailure()
        emptySubjectResult.onFailure {
            assertThat(it.message).isEqualTo(TodokError.MEETING_SUBJECT_EMPTY_VALUE.message)
        }

        val emptyParticipantsResult = useCase.invoke(
            meetingTitle = DEFAULT_MEETING_TITLE,
            meetingSubject = DEFAULT_MEETING_SUBJECT,
            meetingTimeStamp = DEFAULT_TIMESTAMP,
            meetingPlace = DEFAULT_MEETING_PLACE,
            participants = emptyList(),
        )

        assertThat(emptyParticipantsResult).isFailure()

        emptyParticipantsResult.onFailure {
            assertThat(it.message).isEqualTo(TodokError.MEETING_PARTICIPANTS_EMPTY_VALUE.message)
        }
    }

    companion object {
        private const val DEFAULT_MEETING_TITLE = "DEFAULT_MEETING_TITLE"
        private const val DEFAULT_MEETING_SUBJECT = "DEFAULT_MEETING_SUBJECT"
        private const val DEFAULT_TIMESTAMP = 2000L
        private const val DEFAULT_MEETING_PLACE = "ROOM_200"
        private val DEFAULT_PARTICIPANTS_LIST = listOf(
            "participant1",
            "participant2",
            "participant3"
        )
        private const val DEFAULT_PARTICIPANTS_STRING = "participant1,participant2,participant3"
    }
}