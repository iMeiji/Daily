package com.meiji.daily

import android.content.Context

import com.facebook.stetho.Stetho

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by Meiji on 2017/5/2.
 */

object SdkManager {
    fun initStetho(context: Context) {
        Stetho.initializeWithDefaults(context)
    }

    fun initInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder
    }
}
