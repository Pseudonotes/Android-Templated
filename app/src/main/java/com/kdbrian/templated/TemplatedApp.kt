package com.kdbrian.templated

import android.app.Application
import com.kdbrian.templated.di.exampleDotComModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TemplatedApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        startKoin {

            androidLogger()
            androidContext(this@TemplatedApp)

            modules(
                exampleDotComModules,
            )
        }
    }
}