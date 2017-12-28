package com.meiji.daily.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.meiji.daily.data.local.AppDatabase;
import com.meiji.daily.util.RetrofitHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Meiji on 2017/12/21.
 */
@Module
public class AppModule {

    @Named("context")
    @Provides
    @Singleton
    Context provideContext(Context context) {
        return context;
    }

    @Named("application")
    @Provides
    @Singleton
    Application provideApplication(Application application) {
        return application;
    }

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Context context) {
        return new RetrofitHelper(context).getRetrofit();
    }
}
