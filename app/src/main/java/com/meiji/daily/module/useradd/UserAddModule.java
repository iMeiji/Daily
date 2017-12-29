package com.meiji.daily.module.useradd;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.meiji.daily.data.local.AppDatabase;
import com.meiji.daily.di.scope.FragmentScoped;
import com.meiji.daily.util.RxBusHelper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Meiji on 2017/12/28.
 */
@Module
public class UserAddModule {

    private final UserAddView mUserAddView;

    public UserAddModule(UserAddView userAddView) {
        mUserAddView = userAddView;
    }

    @FragmentScoped
    @Provides
    UserAddViewModel provideModel(@Named("application") Application application, AppDatabase appDatabase,
                                  Retrofit retrofit, RxBusHelper rxBusHelper) {
        UserAddViewModel.Factory factory =
                new UserAddViewModel.Factory(application, appDatabase, retrofit, rxBusHelper);
        return ViewModelProviders.of(mUserAddView, factory).get(UserAddViewModel.class);
    }
}
