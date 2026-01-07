package com.flacinc.todok_mp.domain.meetings

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.flacinc.todok_mp.domain.meeting.GetMeetingsUseCase
import com.flacinc.todok_mp.domain.meeting.MeetingRepository
import com.flacinc.todok_mp.domain.meeting.entity.MeetingEntity
import com.flacinc.todok_mp.ui.utils.model.MeetingPlace
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetMeetingsUseCaseUnitTest {
    private val testDispatcher = StandardTestDispatcher()


    @Test
    fun `nominal case - should return success(List of meetings)`() = runTest(testDispatcher) {
        val repository = mock<MeetingRepository>()
        val useCase = GetMeetingsUseCase(repository)

        everySuspend {
            repository.getMeetings()
        } returns flowOf(provideListOfMeetingEntity())

        useCase().test {
            val result = awaitItem()
            awaitComplete()
            assertThat(result).isEqualTo(provideListOfMeetingEntity())

            verifySuspend {
                repository.getMeetings()
            }
        }
    }

    private fun provideListOfMeetingEntity() = List(3) {
        MeetingEntity(
            id = it.toLong(),
            title = "Daily+$it",
            subject = "Standup+$it",
            timestamp = 123456L,
            place = MeetingPlace.ROOM_200.name,
            participants = "Alice,Bob",
        )
    }
}