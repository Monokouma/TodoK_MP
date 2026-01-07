package com.flacinc.todok_mp.di

import com.flacinc.todok_mp.data.database.DatabaseDriverFactory
import com.flacinc.todok_mp.data.database.sql.AppDatabase
import com.flacinc.todok_mp.data.meeting.MeetingRepositoryImpl
import com.flacinc.todok_mp.domain.meeting.MeetingRepository
import org.koin.dsl.module

val dataModule = module {
    single { get<DatabaseDriverFactory>().createDriver() }
    single { AppDatabase(get()) }
    single { get<AppDatabase>().meetingQueries }

    single<MeetingRepository> {
        MeetingRepositoryImpl(get())

    }
}