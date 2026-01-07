package com.flacinc.todok_mp.di

import com.flacinc.todok_mp.domain.meeting.CreateMeetingUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CreateMeetingUseCase(get()) }
}