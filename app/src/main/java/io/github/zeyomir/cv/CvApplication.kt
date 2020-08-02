package io.github.zeyomir.cv

import android.app.Application
import io.github.zeyomir.cv.base.di.ScopeManager
import timber.log.Timber

class CvApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ScopeManager.init(this)

        setupLogging()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
