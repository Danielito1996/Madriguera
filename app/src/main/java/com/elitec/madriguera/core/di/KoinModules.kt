package com.elitec.madriguera.core.di

import com.elitec.madriguera.BuildConfig
import io.appwrite.Client
import io.appwrite.services.TablesDB
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModules {
    fun appWriteModule(): Module {
        return module {
            single {
                Client(get())
                    .setProject(BuildConfig.APPWRITE_PROJECT_ID)
                    .setEndpoint(BuildConfig.APPWRITE_PUBLIC_ENDPOINT)
                    .setLocale("es")
            }
            single {
                TablesDB(get())
            }
        }
    }
}