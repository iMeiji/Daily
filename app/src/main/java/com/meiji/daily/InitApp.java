package com.meiji.daily;

import android.app.Application;
import android.content.Context;

/**
 * Created by Meiji on 2016/12/7.
 */

public class InitApp extends Application {

    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        if (BuildConfig.DEBUG) {
            SdkManager.initStetho(AppContext);
        }
    }
}
