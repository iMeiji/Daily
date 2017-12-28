package com.meiji.daily;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.meiji.daily.di.component.AppComponent;
import com.meiji.daily.di.component.DaggerAppComponent;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Meiji on 2016/12/7.
 */

public class App extends MultiDexApplication {

    public static Context sAppContext;
    public static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent
                .builder()
                .application(this)
                .context(getApplicationContext())
                .build();
        sAppComponent.inject(this);

        sAppContext = getApplicationContext();
        if (BuildConfig.DEBUG) {
            SdkManager.initStetho(this);
        }

        initFabric();
    }

    private void initFabric() {
        // Set up Crashlytics, disabled for debug builds
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit);
    }
}
