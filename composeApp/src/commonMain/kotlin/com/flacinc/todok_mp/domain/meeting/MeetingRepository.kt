package com.flacinc.todok_mp.domain.meeting

import com.flacinc.todok_mp.domain.meeting.entity.CreateMeetingEntity

interface MeetingRepository {
    suspend fun save(creationEntity: CreateMeetingEntity): Result<Unit>
}