package com.meiji.daily.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.meiji.daily.data.local.AppDatabase
import com.meiji.daily.util.RetrofitHelper
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Meiji on 2017/12/21.
 */
@Module
class AppModule {

    @Named("context")
    @Provides
    @Singleton
    internal fun provideContext(context: Context): Context {
        return context
    }

    @Named("application")
    @Provides
    @Singleton
    internal fun provideApplication(application: Application): Application {
        return application
    }

    @Singleton
    @Provides
    internal fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    internal fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(context: Context): Retrofit {
        return RetrofitHelper(context).retrofit
    }
}
