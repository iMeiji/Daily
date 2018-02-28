package com.meiji.daily.util

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.meiji.daily.App
import com.meiji.daily.BuildConfig
import com.meiji.daily.SdkManager
import com.meiji.daily.data.remote.IApi
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Meiji on 2017/4/22.
 */
@Suppress("deprecation")
@Deprecated("")
class RetrofitFactory private constructor() {

    companion object {
        val instance: Retrofit by lazy { RetrofitFactory().init() }
    }

    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     * https://werb.github.io/2016/07/29/%E4%BD%BF%E7%94%A8Retrofit2+OkHttp3%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98%E5%A4%84%E7%90%86/
    </包名> */
    private val cacheControlInterceptor = Interceptor { chain ->
        var request = chain.request()
        if (!NetWorkUtil.isNetworkConnected(App.sAppContext)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }

        val originalResponse = chain.proceed(request)
        if (NetWorkUtil.isNetworkConnected(App.sAppContext)) {
            // 有网络时 设置缓存为默认值
            val cacheControl = request.cacheControl().toString()
            originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build()
        } else {
            // 无网络时 设置超时为1周
            val maxStale = 60 * 60 * 24 * 7
            originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build()
        }
    }

    fun init(): Retrofit {
        // 指定缓存路径,缓存大小 50Mb
        val cache = Cache(File(App.sAppContext.cacheDir, "HttpCache"),
                (1024 * 1024 * 50).toLong())
        // Cookie 持久化
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.sAppContext))

        var builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .cache(cache)
                .addInterceptor(cacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)

        // Log 拦截器
        if (BuildConfig.DEBUG) {
            builder = SdkManager.initInterceptor(builder)
        }

        return Retrofit.Builder()
                .baseUrl(IApi.API_BASE)
                .client(builder.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}
