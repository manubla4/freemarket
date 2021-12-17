package com.manubla.freemarket.view.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.inject.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    storageModule,
                    repositoriesModule,
                    adaptersModule,
                    viewModelsModule
                )
            )
        }
        AndroidThreeTen.init(this)
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}
