package com.meiji.daily.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
