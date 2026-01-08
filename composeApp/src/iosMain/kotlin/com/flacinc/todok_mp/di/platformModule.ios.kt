package com.flacinc.todok_mp.di

import com.flacinc.data.database.DatabaseDriverFactory
import org.koin.dsl.module

actual val platformModule = module {
    single { DatabaseDriverFactory() }
}