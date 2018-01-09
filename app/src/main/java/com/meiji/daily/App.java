package com.meiji.daily;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.meiji.daily.di.component.AppComponent;
import com.meiji.daily.di.component.DaggerAppComponent;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Meiji on 2016/12/7.
 */

public class App extends Application {

    public static Context sAppContext;
    public static AppComponent sAppComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // Android 5.0 之前版本的 Dalvik 可执行文件分包支持
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            MultiDex.install(this);
        }
    }

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
