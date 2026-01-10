package com.flacinc.data.meeting

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.flacinc.data.meeting.mapper.toEntity
import com.flacinc.domain.meeting.MeetingRepository
import com.flacinc.domain.meeting.entity.MeetingEntity
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
    override suspend fun save(meetingEntity: MeetingEntity): Result<Unit> = withContext(
        Dispatchers.IO
    ) {
        try {
            queries.insertMeeting(
                title = meetingEntity.title,
                subject = meetingEntity.subject,
                timestamp = meetingEntity.timestamp,
                room = meetingEntity.room,
                participants = meetingEntity.participants
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
            .map { list -> list.map { it.toEntity() } }

    override suspend fun delete(meetingId: Long): Unit = withContext(Dispatchers.IO) {
        try {
            queries.deleteMeetingById(meetingId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {

        }
    }

    override suspend fun getMeetingById(
        meetingId: Long
    ): Result<MeetingEntity> = withContext(Dispatchers.IO) {
        try {
            val meetingDetail = queries
                .getMeetingById(meetingId)
                .executeAsOneOrNull()
                ?.toEntity() ?: return@withContext Result.failure(Exception("Meeting not found"))

            Result.success(meetingDetail)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}