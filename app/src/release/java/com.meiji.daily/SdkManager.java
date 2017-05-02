package com.meiji.daily;

import android.content.Context;

import okhttp3.OkHttpClient;


/**
 * Created by Meiji on 2017/5/2.
 */

public class SdkManager {
    public static void initStetho(Context context) {
    }

    public static OkHttpClient.Builder initInterceptor(OkHttpClient.Builder builder) {
        return builder;
    }
}
