package com.flacinc.todok_mp

import android.app.Application
import com.flacinc.data.di.databaseModule
import com.flacinc.todok_mp.di.dataModule
import com.flacinc.todok_mp.di.domainModule
import com.flacinc.todok_mp.di.platformModule
import com.flacinc.todok_mp.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                platformModule,
                databaseModule,
                dataModule,
                domainModule,
                uiModule,
            )
        }
    }
}