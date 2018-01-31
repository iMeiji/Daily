package com.meiji.daily.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.meiji.daily.App
import com.meiji.daily.data.local.AppDatabase
import com.meiji.daily.di.module.AppModule
import com.meiji.daily.util.RxBusHelper
import com.meiji.daily.util.SettingHelper
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Meiji on 2017/12/21.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    @get:Named("application")
    val application: Application

    @get:Named("context")
    val context: Context

    val appDatabase: AppDatabase

    val settingHelper: SettingHelper

    val retrofit: Retrofit

    val sharedPreferences: SharedPreferences

    val rxBus: RxBusHelper

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}