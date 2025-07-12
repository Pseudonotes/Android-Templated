package com.kdbrian.templated

import android.app.Application
import timber.log.Timber

class TemplatedApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}