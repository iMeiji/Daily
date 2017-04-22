package com.meiji.daily;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.meiji.daily.utils.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Meiji on 2017/4/22.
 */

public class RetrofitFactory {

    private static final Object Object = new Object();
    private volatile static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        synchronized (Object) {
            if (retrofit == null) {
                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(InitApp.AppContext));
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .cookieJar(cookieJar)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .build();
                retrofit = new Retrofit.Builder()
                        .baseUrl(Api.API_BASE)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
}
