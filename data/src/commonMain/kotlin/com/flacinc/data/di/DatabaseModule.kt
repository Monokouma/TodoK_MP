package com.flacinc.data.di

import com.flacinc.data.database.DatabaseDriverFactory
import com.flacinc.todok_mp.data.database.sql.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { get<DatabaseDriverFactory>().createDriver() }
    single { AppDatabase(get()) }
    single { get<AppDatabase>().meetingQueries }
}