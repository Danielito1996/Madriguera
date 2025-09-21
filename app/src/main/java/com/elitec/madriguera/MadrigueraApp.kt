package com.elitec.madriguera

import android.app.Application
import com.elitec.madriguera.core.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MadrigueraApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MadrigueraApp)
            modules(
                KoinModules.appWriteModule()
            )
        }
    }
}