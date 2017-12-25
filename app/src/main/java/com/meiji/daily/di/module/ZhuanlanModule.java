package com.meiji.daily.di.module;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.meiji.daily.di.scope.FragmentScoped;
import com.meiji.daily.module.zhuanlan.ZhuanlanView;
import com.meiji.daily.module.zhuanlan.ZhuanlanViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Meiji on 2017/12/21.
 */
@Module
public class ZhuanlanModule {

    private final ZhuanlanView mZhuanlanView;
    private final int mType;
    private final Application mApplication;

    public ZhuanlanModule(ZhuanlanView zhuanlanView, int type, Application application) {
        mZhuanlanView = zhuanlanView;
        mType = type;
        mApplication = application;
    }

    @FragmentScoped
    @Provides
    ZhuanlanViewModel provideZhuanlanViewModel() {
        ZhuanlanViewModel.Factory factory =
                new ZhuanlanViewModel.Factory(mApplication, mType);
        return ViewModelProviders.of(mZhuanlanView, factory).get(ZhuanlanViewModel.class);
    }

//    @FragmentScoped
//    @Provides
//    ZhuanlanViewModel provideZhuanlanView(ZhuanlanView view,
//                                               int type) {
//        ZhuanlanViewModel.Factory factory =
//                new ZhuanlanViewModel.Factory(App.mApplication, type);
//        return ViewModelProviders.of(view, factory).get(ZhuanlanViewModel.class);
//    }
}
