package com.flacinc.todok_mp.domain.meetings.fake

import com.flacinc.todok_mp.domain.meeting.MeetingRepository
import com.flacinc.todok_mp.domain.meeting.entity.CreateMeetingEntity
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class FakeMeetingRepository(
    private val shouldFail: Boolean
) : MeetingRepository {

    override suspend fun save(creationEntity: CreateMeetingEntity): Result<Unit> = withContext(
        Dispatchers.IO
    ) {
        try {
            if (shouldFail) Result.failure(Exception("Failed")) else Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}