package io.github.pshegger.seriesdb

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.github.pshegger.seriesdb.di.seriesDBModuleList
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SeriesDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }

            androidContext(this@SeriesDBApplication)

            modules(seriesDBModuleList)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
