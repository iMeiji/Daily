package com.meiji.daily

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.meiji.daily.di.component.AppComponent
import com.meiji.daily.di.component.DaggerAppComponent
import io.fabric.sdk.android.Fabric

/**
 * Created by Meiji on 2016/12/7.
 */

class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        sAppComponent = DaggerAppComponent
                .builder()
                .application(this)
                .context(applicationContext)
                .build()
        sAppComponent.inject(this)

        sAppContext = applicationContext
        if (BuildConfig.DEBUG) {
            SdkManager.initStetho(this)
        }

        initFabric()
    }

    private fun initFabric() {
        // Set up Crashlytics, disabled for debug builds
        val crashlyticsKit = Crashlytics.Builder()
                .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build()

        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit)
    }

    companion object {

        lateinit var sAppContext: Context
        lateinit var sAppComponent: AppComponent
    }
}
