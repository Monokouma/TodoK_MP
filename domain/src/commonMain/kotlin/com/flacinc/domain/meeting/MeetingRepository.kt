package com.flacinc.domain.meeting


import com.flacinc.domain.meeting.entity.CreateMeetingEntity
import com.flacinc.domain.meeting.entity.MeetingEntity
import kotlinx.coroutines.flow.Flow

interface MeetingRepository {
    suspend fun save(creationEntity: CreateMeetingEntity): Result<Unit>
    fun getMeetings(): Flow<List<MeetingEntity>>

    suspend fun delete(meetingId: Long)
}