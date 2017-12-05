package com.meiji.daily;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.google.gson.Gson;
import com.meiji.daily.bean.FooterBean;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.data.local.AppDatabase;

/**
 * Created by Meiji on 2016/12/7.
 */

public class InitApp extends MultiDexApplication {

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

        FooterBean footerBean = new FooterBean();
        ZhuanlanBean zhuanlanBean = new ZhuanlanBean();
        boolean b = ZhuanlanBean.class.isInstance(footerBean);
        System.out.println("InitApp " + b);
    }
}
