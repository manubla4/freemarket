package com.manubla.freemarket.view.app

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.jakewharton.threetenabp.AndroidThreeTen
import com.manubla.freemarket.inject.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ExperimentalPagingApi
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
    }
}
