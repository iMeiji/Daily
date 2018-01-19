package com.meiji.daily

import android.content.Context

import okhttp3.OkHttpClient


/**
 * Created by Meiji on 2018/1/19.
 */

public class SdkManager {

    companion object {
        @JvmStatic
        fun initStetho(context: Context) {
        }

        @JvmStatic
        fun initInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
            return builder
        }
    }
}
