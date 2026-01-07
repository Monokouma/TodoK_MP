package com.flacinc.todok_mp.data.meeting

import com.flacinc.todok_mp.data.meeting.mapper.toDto
import com.flacinc.todok_mp.domain.meeting.MeetingRepository
import com.flacinc.todok_mp.domain.meeting.entity.CreateMeetingEntity
import com.flacinc.todokmp.data.database.sql.MeetingQueries
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class MeetingRepositoryImpl(
    private val queries: MeetingQueries

) : MeetingRepository {
    override suspend fun save(creationEntity: CreateMeetingEntity): Result<Unit> = withContext(
        Dispatchers.IO
    ) {
        try {
            val dto = creationEntity.toDto()

            queries.insertMeeting(
                title = dto.title,
                subject = dto.subject,
                timestamp = dto.timestamp,
                place = dto.meetingPlace,
                participants = dto.participants.joinToString(",")
            )

            Result.success(Unit)

        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}