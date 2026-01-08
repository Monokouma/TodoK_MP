package com.flacinc.todok_mp.domain.meetings


import com.flacinc.domain.meeting.DeleteOldMeetingUseCase
import com.flacinc.domain.meeting.MeetingRepository
import com.flacinc.domain.meeting.entity.MeetingEntity
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class DeleteOldMeetingUseCaseUnitTest {
    private val testDispatcher = StandardTestDispatcher()


    @Test
    fun `nominal case - use case before time now should be deleted`() = runTest(testDispatcher) {
        val repository = mock<MeetingRepository>()
        val useCase = DeleteOldMeetingUseCase(repository)

        everySuspend { repository.delete(0) } returns Unit
        everySuspend { repository.delete(1) } returns Unit
        everySuspend { repository.delete(2) } returns Unit
        everySuspend { repository.delete(3) } returns Unit

        every {
            repository.getMeetings()
        } returns flowOf(
            provideListOfMeetingEntity()
        )

        useCase()

        verifySuspend { repository.getMeetings() }

    }

    private fun provideListOfMeetingEntity() = List(3) {
        MeetingEntity(
            id = it.toLong(),
            title = "Daily+$it",
            subject = "Standup+$it",
            timestamp = when (it) {
                1 -> 123
                2 -> 123
                3 -> 123
                else -> 123
            },
            place = "ROOM_200",
            participants = "Alice,Bob",
        )
    }
}