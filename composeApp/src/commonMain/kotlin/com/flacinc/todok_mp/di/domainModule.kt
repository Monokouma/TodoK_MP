package com.flacinc.todok_mp.di


import com.flacinc.domain.meeting.CreateMeetingUseCase
import com.flacinc.domain.meeting.DeleteOldMeetingUseCase
import com.flacinc.domain.meeting.GetMeetingByIdUseCase
import com.flacinc.domain.meeting.GetMeetingsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CreateMeetingUseCase(meetingRepository = get()) }
    factory { GetMeetingsUseCase(meetingRepository = get()) }
    factory { DeleteOldMeetingUseCase(meetingRepository = get()) }
    factory { GetMeetingByIdUseCase(meetingRepository = get()) }
}