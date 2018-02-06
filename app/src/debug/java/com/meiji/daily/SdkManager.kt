package com.meiji.daily

import android.content.Context

import com.facebook.stetho.Stetho
import com.meiji.daily.util.HttpLoggingInterceptor

import okhttp3.OkHttpClient

/**
 * Created by Meiji on 2017/5/2.
 */

object SdkManager {
    fun initStetho(context: Context) {
        Stetho.initializeWithDefaults(context)
    }

    fun initInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        val interceptor = HttpLoggingInterceptor()
        interceptor.mLevel = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder
    }
}
