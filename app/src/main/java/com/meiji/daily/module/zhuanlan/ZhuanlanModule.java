package com.meiji.daily.module.zhuanlan;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.meiji.daily.Constant;
import com.meiji.daily.data.local.AppDatabase;
import com.meiji.daily.di.scope.FragmentScoped;
import com.meiji.daily.util.RxBusHelper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Meiji on 2017/12/21.
 */
@Module
public class ZhuanlanModule {

    private final ZhuanlanView mZhuanlanView;

    public ZhuanlanModule(ZhuanlanView zhuanlanView) {
        mZhuanlanView = zhuanlanView;
    }

    @FragmentScoped
    @Provides
    ZhuanlanViewModel provideModel(@Named("application") Application application,
                                   int type, AppDatabase appDatabase, Retrofit retrofit, RxBusHelper rxBusHelper) {
        ZhuanlanViewModel.Factory factory =
                new ZhuanlanViewModel.Factory(application, type, appDatabase, retrofit, rxBusHelper);
        return ViewModelProviders.of(mZhuanlanView, factory).get(ZhuanlanViewModel.class);
    }

    @FragmentScoped
    @Provides
    int provideType() {
        Bundle bundle = mZhuanlanView.getArguments();
        int type = Constant.TYPE_PRODUCT;
        if (bundle != null) {
            type = bundle.getInt(ZhuanlanView.ARGUMENT_TYPE, Constant.TYPE_PRODUCT);
        }
        return type;
    }
}
