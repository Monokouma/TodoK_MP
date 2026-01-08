package com.flacinc.todok_mp.di


import com.flacinc.data.meeting.MeetingRepositoryImpl
import com.flacinc.domain.meeting.MeetingRepository
import org.koin.dsl.module

val dataModule = module {
    single<MeetingRepository> {
        MeetingRepositoryImpl(get())
    }
}