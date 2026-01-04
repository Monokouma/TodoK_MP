package com.flacinc.todok_mp

import android.app.Application
import com.flacinc.todok_mp.di.dataModule
import com.flacinc.todok_mp.di.domainModule
import com.flacinc.todok_mp.di.platformModule
import com.flacinc.todok_mp.di.uiModule
import com.flacinc.todok_mp.ui.theme.TodoKMPTheme

import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                platformModule,
                dataModule,
                domainModule,
                uiModule,
            )
        }
    }
}