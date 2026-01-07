package com.flacinc.todok_mp.data.meeting

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.flacinc.todok_mp.data.meeting.mapper.toDto
import com.flacinc.todok_mp.data.meeting.mapper.toEntity
import com.flacinc.todok_mp.domain.meeting.MeetingRepository
import com.flacinc.todok_mp.domain.meeting.entity.CreateMeetingEntity
import com.flacinc.todok_mp.domain.meeting.entity.MeetingEntity
import com.flacinc.todokmp.data.database.sql.MeetingQueries
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MeetingRepositoryImpl(
    private val queries: MeetingQueries,
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

    override fun getMeetings(): Flow<List<MeetingEntity>> =
        queries
            .getAllMeetings()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toDto().toEntity() } }
}