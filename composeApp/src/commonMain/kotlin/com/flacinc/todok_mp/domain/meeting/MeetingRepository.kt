package com.flacinc.todok_mp.domain.meeting

import com.flacinc.todok_mp.domain.meeting.entity.CreateMeetingEntity
import com.flacinc.todok_mp.domain.meeting.entity.MeetingEntity
import kotlinx.coroutines.flow.Flow

interface MeetingRepository {
    suspend fun save(creationEntity: CreateMeetingEntity): Result<Unit>
    fun getMeetings(): Flow<List<MeetingEntity>>
}