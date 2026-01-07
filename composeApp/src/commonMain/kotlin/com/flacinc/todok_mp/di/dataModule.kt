package com.flacinc.todok_mp.di

import com.flacinc.todok_mp.data.meeting.MeetingRepositoryImpl
import com.flacinc.todok_mp.domain.meeting.MeetingRepository
import org.koin.dsl.module

val dataModule = module {
    single<MeetingRepository> {
        MeetingRepositoryImpl()

    }
}