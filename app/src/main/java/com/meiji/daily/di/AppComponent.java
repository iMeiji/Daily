package com.meiji.daily.di;

import android.app.Application;
import android.content.Context;

import com.meiji.daily.App;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Meiji on 2017/12/21.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);

    @Named("application")
    Application application();

    @Named("context")
    Context context();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }

}