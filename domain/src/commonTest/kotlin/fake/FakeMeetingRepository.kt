package com.flacinc.todok_mp.domain.meetings.fake


import com.flacinc.domain.meeting.MeetingRepository
import com.flacinc.domain.meeting.entity.CreateMeetingEntity
import com.flacinc.domain.meeting.entity.MeetingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMeetingRepository(

) : MeetingRepository {
    override suspend fun save(creationEntity: CreateMeetingEntity): Result<Unit> =
        Result.success(Unit)

    override fun getMeetings(): Flow<List<MeetingEntity>> = flowOf(emptyList())

    override suspend fun delete(meetingId: Long) {

    }
}