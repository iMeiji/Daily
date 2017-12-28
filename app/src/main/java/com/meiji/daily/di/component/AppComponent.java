package com.meiji.daily.di.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.meiji.daily.App;
import com.meiji.daily.data.local.AppDatabase;
import com.meiji.daily.di.module.AppModule;
import com.meiji.daily.util.SettingHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Meiji on 2017/12/21.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);

    @Named("application")
    Application getApplication();

    @Named("context")
    Context getContext();

    AppDatabase getAppDatabase();

    SettingHelper getSettingHelper();

    Retrofit getRetrofit();

    SharedPreferences getSharedPreferences();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }

}