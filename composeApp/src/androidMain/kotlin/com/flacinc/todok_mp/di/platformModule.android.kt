package com.flacinc.todok_mp.di

import com.flacinc.todok_mp.data.database.DatabaseDriverFactory
import org.koin.dsl.module

actual val platformModule = module {
    single { DatabaseDriverFactory(get()) }

}