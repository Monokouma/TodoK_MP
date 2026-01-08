package com.flacinc.todok_mp.di


import com.flacinc.domain.meeting.CreateMeetingUseCase
import com.flacinc.domain.meeting.DeleteOldMeetingUseCase
import com.flacinc.domain.meeting.GetMeetingsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CreateMeetingUseCase(get()) }
    factory { GetMeetingsUseCase(get()) }
    factory { DeleteOldMeetingUseCase(get()) }
}