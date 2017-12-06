package com.meiji.daily;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.google.gson.Gson;
import com.meiji.daily.data.local.AppDatabase;

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
        sDatabase = AppDatabase.getsInstance(this);
        application = this;
        if (BuildConfig.DEBUG) {
            SdkManager.initStetho(this);
        }
    }
}
