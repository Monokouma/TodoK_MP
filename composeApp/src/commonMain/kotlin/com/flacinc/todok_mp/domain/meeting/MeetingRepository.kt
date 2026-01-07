package com.flacinc.todok_mp.domain.meeting

import com.flacinc.todok_mp.domain.meeting.entity.MeetingCreationEntity

interface MeetingRepository {
    suspend fun save(creationEntity: MeetingCreationEntity): Result<Unit>
}