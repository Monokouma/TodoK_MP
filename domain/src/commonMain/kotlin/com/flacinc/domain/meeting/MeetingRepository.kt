package com.flacinc.domain.meeting


import com.flacinc.domain.meeting.entity.MeetingEntity
import kotlinx.coroutines.flow.Flow

interface MeetingRepository {
    suspend fun save(meetingEntity: MeetingEntity): Result<Unit>
    fun getMeetings(): Flow<List<MeetingEntity>>

    suspend fun delete(meetingId: Long)
    suspend fun getMeetingById(meetingId: Long): Result<MeetingEntity>
}