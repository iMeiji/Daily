package com.meiji.daily;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.gson.Gson;
import com.meiji.daily.data.local.AppDatabase;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Meiji on 2016/12/7.
 */

public class InitApp extends MultiDexApplication {

    public static Context sAppContext;
    public static Gson sGson;
    public static AppDatabase sDatabase;

    public static InitApp application;


    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        sGson = new Gson();
        sDatabase = AppDatabase.getInstance(this);
        application = this;
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
