package com.flacinc.todok_mp.di

import com.flacinc.data.di.databaseModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            platformModule,
            databaseModule,
            dataModule,
            domainModule,
            uiModule,
        )
    }
}