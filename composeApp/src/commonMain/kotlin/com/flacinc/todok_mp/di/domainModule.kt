package com.flacinc.todok_mp.di

import com.flacinc.todok_mp.domain.meeting.CreateMeetingUseCase
import com.flacinc.todok_mp.domain.meeting.DeleteOldMeetingUseCase
import com.flacinc.todok_mp.domain.meeting.GetMeetingsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CreateMeetingUseCase(get()) }
    factory { GetMeetingsUseCase(get()) }
    factory { DeleteOldMeetingUseCase(get()) }
}