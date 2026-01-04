package com.flacinc.todok_mp.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            platformModule,
            dataModule,
            domainModule,
            uiModule,
        )
    }
}