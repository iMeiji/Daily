package com.meiji.daily.injector.module;

import com.meiji.daily.mvp.zhuanlan.IZhuanlan;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanPresenter;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Meiji on 2017/7/10.
 */
@Module
public class ZhuanlanModule {

    private final ZhuanlanView view;

    public ZhuanlanModule(ZhuanlanView view) {
        this.view = view;
    }

    @Provides
    public IZhuanlan.Presenter providePresenter() {
        return new ZhuanlanPresenter(view);
    }
}
