package com.flacinc.todok_mp.data.meeting

import com.flacinc.todok_mp.domain.meeting.MeetingRepository
import com.flacinc.todok_mp.domain.meeting.entity.MeetingCreationEntity

class MeetingRepositoryImpl(

) : MeetingRepository {
    override suspend fun save(creationEntity: MeetingCreationEntity): Result<Unit> {
        TODO("Not yet implemented")
    }
}