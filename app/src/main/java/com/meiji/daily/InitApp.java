package com.meiji.daily;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.meiji.daily.data.local.AppDatabase;

/**
 * Created by Meiji on 2016/12/7.
 */

public class InitApp extends Application {

    public static Context AppContext;
    public static Gson gson;
    public static AppDatabase db;

    public static InitApp application;


    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        gson = new Gson();
        db = AppDatabase.getsInstance(this);
        application = this;
        if (BuildConfig.DEBUG) {
            SdkManager.initStetho(this);
        }
    }
}
