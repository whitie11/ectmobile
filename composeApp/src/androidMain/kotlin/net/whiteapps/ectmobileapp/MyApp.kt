package net.whiteapps.ectmobileapp

import android.app.Application
import net.whiteapps.ectmobileapp.common.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApp)
        }
    }
}