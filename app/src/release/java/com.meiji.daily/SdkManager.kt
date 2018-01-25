package com.meiji.daily

import android.content.Context

import okhttp3.OkHttpClient


/**
 * Created by Meiji on 2018/1/19.
 */

object SdkManager {

    fun initStetho(context: Context) {
    }

    fun initInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
    }
}
